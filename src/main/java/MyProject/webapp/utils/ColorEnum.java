package MyProject.webapp.utils;

public enum ColorEnum {
    RED(1L, "Red"),
    GREEN(2L, "Green"),
    BLUE(3L, "Blue"),
    PURPLE(4L, "Purple");

    private final Long id;
    private final String color;

    ColorEnum(Long id, String color) {
        this.id = id;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public static String getColorById(Long id) {
        for (ColorEnum colorEnum : ColorEnum.values()) {
            if (colorEnum.getId().equals(id)) {
                return colorEnum.getColor();
            }
        }
        return null; // Trả về null nếu không tìm thấy ID
    }
}

