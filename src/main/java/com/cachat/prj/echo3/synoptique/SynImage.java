package com.cachat.prj.echo3.synoptique;

/**
 *
 * @author scachat
 */
public class SynImage extends SynObject {
    /**
     * l'uid de la vue
     */
    private String viewId;
    /**
     * le content type
     */
    protected String contentType;
    public static final String VIEW_ID_PROPERTY="viewId";
    public static final String CONTENT_TYPE_PROPERTY="contentType";
    
    public void setViewId(String viewId){
        set(VIEW_ID_PROPERTY,viewId);
    }
    public String getViewId(){
        return (String) get(VIEW_ID_PROPERTY);
    }
    public void setContentType(String contentType){
        set(CONTENT_TYPE_PROPERTY,contentType);
    }
    public String getContentType(){
        return (String) get(CONTENT_TYPE_PROPERTY);
    }
}
