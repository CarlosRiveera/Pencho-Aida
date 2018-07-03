package com.ionicframework.penchoyaida233650.util;

import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.os.Looper;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Santiago on 01/06/2016.
 */
public class RemoteControlClientCompat {


    private static final String TAG = "RemoteControlCompat";

    private static Class sRemoteControlClientClass;

    // RCC short for RemoteControlClient
    private static Method sRCCEditMetadataMethod;
    private static Method sRCCSetPlayStateMethod;
    private static Method sRCCSetTransportControlFlags;

    private static boolean sHasRemoteControlAPIs = false;

    static {
        try {
            ClassLoader classLoader = RemoteControlClientCompat.class.getClassLoader();
            sRemoteControlClientClass = getActualRemoteControlClientClass(classLoader);
            // dynamically populate the playstate and flag values in case they change
            // in future versions.
            for (Field field : RemoteControlClientCompat.class.getFields()) {
                try {
                    Field realField = sRemoteControlClientClass.getField(field.getName());
                    Object realValue = realField.get(null);
                    field.set(null, realValue);
                } catch (NoSuchFieldException e) {
                    Log.w(TAG, "Could not get real field: " + field.getName());
                } catch (IllegalArgumentException e) {
                    Log.w(TAG, "Error trying to pull field value for: " + field.getName()
                            + " " + e.getMessage());
                } catch (IllegalAccessException e) {
                    Log.w(TAG, "Error trying to pull field value for: " + field.getName()
                            + " " + e.getMessage());
                }
            }

            // get the required public methods on RemoteControlClient
            sRCCEditMetadataMethod = sRemoteControlClientClass.getMethod("editMetadata",
                    boolean.class);
            sRCCSetPlayStateMethod = sRemoteControlClientClass.getMethod("setPlaybackState",
                    int.class);
            sRCCSetTransportControlFlags = sRemoteControlClientClass.getMethod(
                    "setTransportControlFlags", int.class);

            sHasRemoteControlAPIs = true;
        } catch (ClassNotFoundException e) {
            // Silently fail when running on an OS before ICS.
        } catch (NoSuchMethodException e) {
            // Silently fail when running on an OS before ICS.
        } catch (IllegalArgumentException e) {
            // Silently fail when running on an OS before ICS.
        } catch (SecurityException e) {
            // Silently fail when running on an OS before ICS.
        }
    }

    public static Class getActualRemoteControlClientClass(ClassLoader classLoader)
            throws ClassNotFoundException {
        return classLoader.loadClass("android.media.RemoteControlClient");
    }

    private Object mActualRemoteControlClient;

    public RemoteControlClientCompat(PendingIntent pendingIntent) {
        if (!sHasRemoteControlAPIs) {
            return;
        }
        try {
            mActualRemoteControlClient =
                    sRemoteControlClientClass.getConstructor(PendingIntent.class)
                            .newInstance(pendingIntent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RemoteControlClientCompat(PendingIntent pendingIntent, Looper looper) {
        if (!sHasRemoteControlAPIs) {
            return;
        }

        try {
            mActualRemoteControlClient =
                    sRemoteControlClientClass.getConstructor(PendingIntent.class, Looper.class)
                            .newInstance(pendingIntent, looper);
        } catch (Exception e) {
            Log.e(TAG, "Error creating new instance of " + sRemoteControlClientClass.getName(), e);
        }
    }


    public class MetadataEditorCompat {

        private Method mPutStringMethod;
        private Method mPutBitmapMethod;
        private Method mPutLongMethod;
        private Method mClearMethod;
        private Method mApplyMethod;

        private Object mActualMetadataEditor;

        /**
         * The metadata key for the content artwork / album art.
         */
        public final static int METADATA_KEY_ARTWORK = 100;

        private MetadataEditorCompat(Object actualMetadataEditor) {
            if (sHasRemoteControlAPIs && actualMetadataEditor == null) {
                throw new IllegalArgumentException("Remote Control API's exist, " +
                        "should not be given a null MetadataEditor");
            }
            if (sHasRemoteControlAPIs) {
                Class metadataEditorClass = actualMetadataEditor.getClass();

                try {
                    mPutStringMethod = metadataEditorClass.getMethod("putString",
                            int.class, String.class);
                    mPutBitmapMethod = metadataEditorClass.getMethod("putBitmap",
                            int.class, Bitmap.class);
                    mPutLongMethod = metadataEditorClass.getMethod("putLong",
                            int.class, long.class);
                    mClearMethod = metadataEditorClass.getMethod("clear", new Class[]{});
                    mApplyMethod = metadataEditorClass.getMethod("apply", new Class[]{});
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
            mActualMetadataEditor = actualMetadataEditor;
        }

        /**
         * Adds textual information to be displayed.
         * Note that none of the information added after {@link #apply()} has been called,
         * will be displayed.
         *
         * @param key   The identifier of a the metadata field to set. Valid values are
         *              {@link android.media.MediaMetadataRetriever#METADATA_KEY_ALBUM},
         *              {@link android.media.MediaMetadataRetriever#METADATA_KEY_ALBUMARTIST},
         *              {@link android.media.MediaMetadataRetriever#METADATA_KEY_TITLE},
         *              {@link android.media.MediaMetadataRetriever#METADATA_KEY_ARTIST},
         *              {@link android.media.MediaMetadataRetriever#METADATA_KEY_AUTHOR},
         *              {@link android.media.MediaMetadataRetriever#METADATA_KEY_COMPILATION},
         *              {@link android.media.MediaMetadataRetriever#METADATA_KEY_COMPOSER},
         *              {@link android.media.MediaMetadataRetriever#METADATA_KEY_DATE},
         *              {@link android.media.MediaMetadataRetriever#METADATA_KEY_GENRE},
         *              {@link android.media.MediaMetadataRetriever#METADATA_KEY_TITLE},
         *              {@link android.media.MediaMetadataRetriever#METADATA_KEY_WRITER}.
         * @param value The text for the given key, or {@code null} to signify there is no valid
         *              information for the field.
         * @return Returns a reference to the same MetadataEditor object, so you can chain put
         * calls together.
         */
        public MetadataEditorCompat putString(int key, String value) {
            if (sHasRemoteControlAPIs) {
                try {
                    mPutStringMethod.invoke(mActualMetadataEditor, key, value);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
            return this;
        }

        /**
         * Sets the album / artwork picture to be displayed on the remote control.
         *
         * @param key    the identifier of the bitmap to set. The only valid value is
         *               {@link #METADATA_KEY_ARTWORK}
         * @param bitmap The bitmap for the artwork, or null if there isn't any.
         * @return Returns a reference to the same MetadataEditor object, so you can chain put
         * calls together.
         * @throws IllegalArgumentException
         * @see android.graphics.Bitmap
         */
        public MetadataEditorCompat putBitmap(int key, Bitmap bitmap) {
            if (sHasRemoteControlAPIs) {
                try {
                    mPutBitmapMethod.invoke(mActualMetadataEditor, key, bitmap);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
            return this;
        }

        /**
         * Adds numerical information to be displayed.
         * Note that none of the information added after {@link #apply()} has been called,
         * will be displayed.
         *
         * @param key   the identifier of a the metadata field to set. Valid values are
         *              {@link android.media.MediaMetadataRetriever#METADATA_KEY_CD_TRACK_NUMBER},
         *              {@link android.media.MediaMetadataRetriever#METADATA_KEY_DISC_NUMBER},
         *              {@link android.media.MediaMetadataRetriever#METADATA_KEY_DURATION} (with a value
         *              expressed in milliseconds),
         *              {@link android.media.MediaMetadataRetriever#METADATA_KEY_YEAR}.
         * @param value The long value for the given key
         * @return Returns a reference to the same MetadataEditor object, so you can chain put
         * calls together.
         * @throws IllegalArgumentException
         */
        public MetadataEditorCompat putLong(int key, long value) {
            if (sHasRemoteControlAPIs) {
                try {
                    mPutLongMethod.invoke(mActualMetadataEditor, key, value);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
            return this;
        }

        /**
         * Clears all the metadata that has been set since the MetadataEditor instance was
         * created with {@link android.media.RemoteControlClient#editMetadata(boolean)}.
         */
        public void clear() {
            if (sHasRemoteControlAPIs) {
                try {
                    mClearMethod.invoke(mActualMetadataEditor, (Object[]) null);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }

        /**
         * Associates all the metadata that has been set since the MetadataEditor instance was
         * created with {@link android.media.RemoteControlClient#editMetadata(boolean)}, or since
         * {@link #clear()} was called, with the RemoteControlClient. Once "applied", this
         * MetadataEditor cannot be reused to edit the RemoteControlClient's metadata.
         */
        public void apply() {
            if (sHasRemoteControlAPIs) {
                try {
                    mApplyMethod.invoke(mActualMetadataEditor, (Object[]) null);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * Creates a {@link android.media.RemoteControlClient.MetadataEditor}.
     *
     * @param startEmpty Set to false if you want the MetadataEditor to contain the metadata that
     *                   was previously applied to the RemoteControlClient, or true if it is to be created empty.
     * @return a new MetadataEditor instance.
     */
    public MetadataEditorCompat editMetadata(boolean startEmpty) {
        Object metadataEditor;
        if (sHasRemoteControlAPIs) {
            try {
                metadataEditor = sRCCEditMetadataMethod.invoke(mActualRemoteControlClient,
                        startEmpty);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            metadataEditor = null;
        }
        return new MetadataEditorCompat(metadataEditor);
    }

    /**
     * Sets the current playback state.
     *
     * @param state The current playback state, one of the following values:
     *              {@link android.media.RemoteControlClient#PLAYSTATE_STOPPED},
     *              {@link android.media.RemoteControlClient#PLAYSTATE_PAUSED},
     *              {@link android.media.RemoteControlClient#PLAYSTATE_PLAYING},
     *              {@link android.media.RemoteControlClient#PLAYSTATE_FAST_FORWARDING},
     *              {@link android.media.RemoteControlClient#PLAYSTATE_REWINDING},
     *              {@link android.media.RemoteControlClient#PLAYSTATE_SKIPPING_FORWARDS},
     *              {@link android.media.RemoteControlClient#PLAYSTATE_SKIPPING_BACKWARDS},
     *              {@link android.media.RemoteControlClient#PLAYSTATE_BUFFERING},
     *              {@link android.media.RemoteControlClient#PLAYSTATE_ERROR}.
     */
    public void setPlaybackState(int state) {
        if (sHasRemoteControlAPIs) {
            try {
                sRCCSetPlayStateMethod.invoke(mActualRemoteControlClient, state);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Sets the flags for the media transport control buttons that this client supports.
     *
     * @param transportControlFlags A combination of the following flags:
     *                              {@link android.media.RemoteControlClient#FLAG_KEY_MEDIA_PREVIOUS},
     *                              {@link android.media.RemoteControlClient#FLAG_KEY_MEDIA_REWIND},
     *                              {@link android.media.RemoteControlClient#FLAG_KEY_MEDIA_PLAY},
     *                              {@link android.media.RemoteControlClient#FLAG_KEY_MEDIA_PLAY_PAUSE},
     *                              {@link android.media.RemoteControlClient#FLAG_KEY_MEDIA_PAUSE},
     *                              {@link android.media.RemoteControlClient#FLAG_KEY_MEDIA_STOP},
     *                              {@link android.media.RemoteControlClient#FLAG_KEY_MEDIA_FAST_FORWARD},
     *                              {@link android.media.RemoteControlClient#FLAG_KEY_MEDIA_NEXT}
     */
    public void setTransportControlFlags(int transportControlFlags) {
        if (sHasRemoteControlAPIs) {
            try {
                sRCCSetTransportControlFlags.invoke(mActualRemoteControlClient,
                        transportControlFlags);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public final Object getActualRemoteControlClientObject() {
        return mActualRemoteControlClient;
    }
}
