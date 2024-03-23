package vn.edu.ptit.sqa.config;

import java.util.HashSet;
import java.util.List;

public interface AppProperties {
    interface PAGINATION {
        int PAGE_SIZE = 10;

        String DEFAULT_PAGE_NUMBER = "0";

        String DEFAULT_PAGE_SIZE = "10";

    }

    interface EMAIL_DETAIL_STATUS {
        int INIT = 0;
        int SEND_SUCCESS = 1;
        int SEND_FAIL = -1;
    }

    interface EMAIL_SERVER{
        String HOST = "smtp.gmail.com";
        Integer PORT = 587;
        String USERNAME = "huynhndev0403@gmail.com";
        String PASSWORD = "";
    }
    interface EMAIL_ATTACHMENT{
        HashSet<String> VALID_FILE_TYPE = new HashSet<>(List.of(".doc", ".docx", ".xlsx", ".csv", ".pdf",
                ".zip", ".rar", ".pptx", ".jpg", ".jpeg", ".gif", ".png", ".svg"));
        String REMOTE_FILE_PATH = "attachments/";
    }
    interface EMAIL_PROPERTY {
        String PATTERN_PROPERTY = "<app-ht-tien-nuoc>.+?</app-ht-tien-nuoc>";
    }

    interface PRICE_LIST {
        interface STATUS {
            Integer ACTIVE = 1;
            Integer DELETED = -1;
        }
    }
}
