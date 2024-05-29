package by.waitaty.learnlanguage.entity;

import lombok.Getter;

@Getter
public enum Language {
    RU("Russian"),
    PL("Polish"),
    EN("English"),
    AR("Arabic"),
    BE("Belarusian"),
    BG("Bulgarian"),
    CS("Czech"),
    CY("Welsh"),
    DA("Danish"),
    DE("German"),
    EL("Greek"),
    EO("Esperanto"),
    ES("Spanish"),
    ET("Estonian"),
    FI("Finnish"),
    FR("French"),
    GA("Irish"),
    GD("Scottish"),
    HU("Hungarian"),
    HY("Armenian"),
    ID("Indonesian"),
    IS("Icelandic"),
    IT("Italian"),
    JA("Japanese"),
    KO("Korean"),
    LT("Lithuanian"),
    LV("Latvian"),
    MK("Macedonian"),
    MN("Mongolian"),
    MO("Moldavian"),
    NE("Nepali"),
    NL("Dutch"),
    NN("Norwegian"),
    PT("Portuguese"),
    RO("Romanian"),
    SK("Slovak"),
    SL("Slovenian"),
    SQ("Albanian"),
    SR("Serbian"),
    SV("Swedish"),
    TH("Thai"),
    TR("Turkish"),
    UK("Ukrainian"),
    VI("Vietnamese"),
    YI("Yiddish"),
    ZH("Chinese");

    Language(String id) {
        this.id = id;
    }

    private final String id;

    public static Language getLanguageFromString(String id) {
        for (Language language : Language.values()) {
            if (language.getId().equalsIgnoreCase(id)) {
                return language;
            }
        }
        return null;
    }
}
