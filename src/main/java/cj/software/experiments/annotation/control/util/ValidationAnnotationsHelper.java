package cj.software.experiments.annotation.control.util;

import java.lang.annotation.Annotation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationAnnotationsHelper {
    private static final Pattern PTN_GROUPS = Pattern.compile(".*groups=\\{(.*?)\\}.*");

    private static final Pattern PTN_CLASS = Pattern.compile("(.*)\\.class");

    public String[] determineGroups(Annotation annotation) {
        String[] result;
        String asString = annotation.toString();
        Matcher matcherGroups = PTN_GROUPS.matcher(asString);
        if (matcherGroups.matches()) {
            String groups = matcherGroups.group(1);
            String[] split = groups.split(",");
            int count = split.length;
            result = new String[count];
            for (int i = 0; i < count; i++) {
                String name = split[i].trim();
                Matcher matcherClass = PTN_CLASS.matcher(name);
                if (matcherClass.matches()) {
                    name = matcherClass.group(1);
                }
                result[i] = name;
            }
        } else {
            result = new String[]{};
        }
        return result;
    }
}
