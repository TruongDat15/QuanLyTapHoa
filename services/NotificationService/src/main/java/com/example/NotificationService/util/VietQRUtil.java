package com.example.NotificationService.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class VietQRUtil {

    public static String createVietQRLink(
            String bankId,
            String accountNo,
            String template,
            double amount,
            String description,
            String accountName
    ) {
        try {
            String descEncoded = URLEncoder.encode(description, StandardCharsets.UTF_8.toString());
            String nameEncoded = URLEncoder.encode(accountName, StandardCharsets.UTF_8.toString());

            return String.format(
                    "https://img.vietqr.io/image/%s-%s-%s.png?amount=%f&addInfo=%s&accountName=%s",
                    bankId,
                    accountNo,
                    template,
                    amount,
                    descEncoded,
                    nameEncoded
            );

        } catch (Exception e) {
            throw new RuntimeException("Error encoding VietQR params", e);
        }
    }
}
