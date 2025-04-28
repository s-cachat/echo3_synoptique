package com.cachat.prj.echo3.synoptique;

/**
 *
 * @author scachat
 */
public class SynText extends SynObject {

    public static final String TEXT_PROPERTY = "text";
    public static final String FONT_SIZE_PROPERTY = "fontSize";
    public static final String FONT_STYLE_PROPERTY = "fontStyle";
    public static final String FONT_FAMILY_PROPERTY = "fontFamily";
    public static final String ALIGNMENT_PROPERTY = "alignment";
    public static final String COLOR_PROPERTY = "color";

    public SynText(double left, double top, double width, double height, String text, double fontSize) {
        super(left, top, width, height, null);
        setText(text);
        setFontSize(fontSize);
    }

    public void setText(String text) {
        set(TEXT_PROPERTY, text);
    }

    public String getText() {
        return (String) get(TEXT_PROPERTY);
    }

    public void setFontSize(double fontSize) {
        set(FONT_SIZE_PROPERTY, (Double) fontSize);
    }

    public Double getFontSize() {
        return (Double) get(FONT_SIZE_PROPERTY);
    }

    public void setFontStyle(String fontStyle) {
        set(FONT_STYLE_PROPERTY, fontStyle);
    }

    public String getFontStyle() {
        return (String) get(FONT_STYLE_PROPERTY);
    }

    public void setFontFamily(String fontFamily) {
        set(FONT_FAMILY_PROPERTY, fontFamily);
    }

    public String getFontFamily() {
        return (String) get(FONT_FAMILY_PROPERTY);
    }

    /**
     * fixe l'alignement horizontal
     *
     * @param alignment une valeur de Alignment
     */
    public void setTextAlign(Integer alignment) {
        set(ALIGNMENT_PROPERTY, alignment);
    }

    public Integer getTextAlign() {
        return (Integer) get(ALIGNMENT_PROPERTY);
    }

    public void setColor(int color) {
        set(COLOR_PROPERTY,color);
    }
    public int getColor(){
        return (Integer) get(COLOR_PROPERTY);
    }


}
