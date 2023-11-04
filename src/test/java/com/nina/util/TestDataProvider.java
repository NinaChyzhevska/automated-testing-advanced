package com.nina.util;

public class TestDataProvider {
    public static Object[][] getDashboardDataForSearchTest() {
        return new Object[][] {
                {"Chinese chars 是一个带有特殊字符的消息", "description jaCNlF9gqMvCEQRirEWnMFo0DIaXPC2ASqrdShK2meX4N58pLUSrNxuS6IudJOKC0F3X0SUMCXAHUwybE8ZPaTqPB"},
                {"desc empty", ""},
                {"Boundary 128 Positive Test naCNlF9gqMvCEQRirEWnMFo0DIaXPC2ASqrdShK2meX4N58pLUSrNxuS6IudJOKC0F3X0SUMCXAHUwybE8ZPaTqPB123456trtrtf", "desc"},
                {"1234567 Numbers", "desc"},
                {"Cyrillic characters абвгдЇьґєя", "desc"},
                {"<>?][}{|/&$^*;:!@#~% other common special chars", "desc"},
                {"one", "desc"},
        };
    }

    public static Object[][] getDashboardDataForPositiveTest() {
        return new Object[][] {
                {"Chinese chars 这是一个带有特殊字符的消息", "description jaCNlF9gqMvCEQRirEWnMFo0DIaXPC2ASqrdShK2meX4N58pLUSrNxuS6IudJOKC0F3X0SUMCXAHUwybE8ZPaTqPB"},
                {"empty desc", ""},
                {"Boundary 128 Positive Test jaCNlF9gqMvCEQRirEWnMFo0DIaXPC2ASqrdShK2meX4N58pLUSrNxuS6IudJOKC0F3X0SUMCXAHUwybE8ZPaTqPB123456trtrtf", "desc"},
                {"12345 Numbers", "desc"},
                {"Cyrillic characters абвгдЇьґє", "desc"},
                {"&<>?][}{|/$^*;:!@#~% other common special chars", "desc"},
                {"min", "desc"},
        };
    }

    public static Object[][] getDashboardDataForNegativeTest() {
        return new Object[][] {
                {"t2", "negativeTest"},
                {"MaxCharsLength OutOfBounds NegativeTest jaCNlF9gqMvCEQRirEWnMFo0DIaXPC2ASqrdShK2meX4N58pLUSrNxuS6IudJOKC0F3X0SUMCXAHUwybE8ZPaTqPB", "desc"}
        };
    }
}
