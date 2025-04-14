package com.cachat.prj.echo3.synoptique;

/**
 *
 * @author scachat
 */
public class SynText extends SynObject {
    
    private SynViewText textView;
    
    public SynText(double left, double top, double width, double height, String text, double fontSize) {
        super(left, top, width, height, null);
        this.textView = new SynViewText(text, fontSize);
        setView(textView);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setFontSize(double fontSize) {
        textView.setFontSize(fontSize);
    }

    public void setUnderline(boolean underline) {
        textView.setUnderline(underline);
    }

    public void setLinethrough(boolean linethrough) {
        textView.setLinethrough(linethrough);
    }

    public void setOverline(boolean overline) {
        textView.setOverline(overline);
    }

    public void setFontStyle(String fontStyle) {
        textView.setFontStyle(fontStyle);
    }

    public void setFontFamily(String fontFamily) {
        textView.setFontFamily(fontFamily);
    }

    public void setTextAlign(SynViewText.TextAlign textAlign) {
        textView.setTextAlign(textAlign);
    }

    public void setTextBackgroundColor(int textBackgroundColor) {
        textView.setTextBackgroundColor(textBackgroundColor);
    }

    public void setFill(int fill) {
        textView.setFill(fill);
    }

    public void setStroke(int stroke) {
        textView.setStroke(stroke);
    }

    
    
}
