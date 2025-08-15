package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FeastMenu implements Serializable {
    private static final Map<String, SetMenu> feastMenu = new HashMap<>();

    public static boolean LoadFeastMenu(String csvPath) {
        feastMenu.clear();
        Path path = Paths.get(csvPath);
        if (!Files.exists(path)) return false;

        boolean flag = false;
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                java.util.List<String> parts = splitCsv(line);
                if (parts.size() < 3) continue;

                String code  = safeTrim(parts.get(0));
                String name  = safeTrim(parts.get(1));
                String priceStr = safeTrim(parts.get(2));
                long price  = parsePrice(priceStr);

                StringBuilder ing = new StringBuilder();
                for (int i = 3; i < parts.size(); i++) {
                    String s = safeTrim(parts.get(i));
                    if (s == null || s.isEmpty()) continue;
                    if (ing.length() > 0) ing.append(", ");
                    ing.append(s);
                }
                String ingredients = ing.toString();

                feastMenu.put(code, new SetMenu(code, name, price, priceStr, ingredients));
                flag = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    public static List<SetMenu> getMenusSortedByPrice() {
        ArrayList<SetMenu> list = new ArrayList<>(feastMenu.values());
        Collections.sort(list, new Comparator<SetMenu>() {
            @Override
            public int compare(SetMenu a, SetMenu b) {
                int byPrice = Long.compare(a.getPrice(), b.getPrice());
                if (byPrice != 0) return byPrice;
                return a.getCode().compareToIgnoreCase(b.getCode());
            }
        });
        return list;
    }

    public static SetMenu SearchByCode(String code) {
        return feastMenu.get(code);
    }

    public static boolean IsExist() {
        return !feastMenu.isEmpty();
    }

    private static long parsePrice(String s) {
        if (s == null) return 0L;
        String digits = s.replaceAll("[^0-9]", "");
        return digits.isEmpty() ? 0L : Long.parseLong(digits);
    }

    private static String safeTrim(String s) {
        return (s == null) ? null : s.replace("\uFEFF", "").trim();
    }

    private static java.util.List<String> splitCsv(String line) {
        java.util.List<String> cols = new java.util.ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    sb.append('"'); i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                cols.add(trimOuterQuotes(sb.toString().trim()));
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        cols.add(trimOuterQuotes(sb.toString().trim()));
        return cols;
    }

    private static String trimOuterQuotes(String s) {
        if (s != null && s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

    public static boolean isContainCode(String code) {
        for (SetMenu setMenu : feastMenu.values()) {
            if (setMenu.getCode().equalsIgnoreCase(code.trim()))
                return true;
        }
        return false;
    }
}