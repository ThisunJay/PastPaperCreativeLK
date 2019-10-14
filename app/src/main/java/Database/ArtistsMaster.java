package Database;

import android.provider.BaseColumns;

public final class ArtistsMaster {

    public static class Artists implements BaseColumns {
        public static final String Table_Name_ArtistDetails = "ArtistDetails";
        public static final String Column_ArtistName = "artistName";
    }

    public static class Photographs implements BaseColumns{
        public static final String Table_Name_PhotographDetails = "PhotographDetails";
        public static final String Column_PhotographName = "PhotographDetails";
        public static final String Column_ArtistId = "artistID";
        public static final String Column_PhotographCategory = "PhotoCategory";
        public static final String Column_Image = "image";
    }
}
