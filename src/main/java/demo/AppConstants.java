package demo;

import java.util.UUID;

public class AppConstants {

        public static final Integer DEFAULT_PAGE_NUMBER = 0;
        public  static final Integer DEFAULT_PAGE_SIZE = 10;
        public static final String DEFAULT_SORT_BY = "createdAt";
        public static final String DEFAULT_SORT_DIRECTION = "desc";

        public static final UUID convertToUUID(String id){
                        return UUID.fromString(id);
                }



}
