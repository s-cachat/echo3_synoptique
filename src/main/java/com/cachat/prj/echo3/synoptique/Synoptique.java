package com.cachat.prj.echo3.synoptique;

import static com.cachat.prj.echo3.ng.able.Heightable.PROPERTY_HEIGHT;
import com.cachat.prj.echo3.ng.able.Positionable;
import static com.cachat.prj.echo3.ng.able.Positionable.PROPERTY_BOTTOM;
import static com.cachat.prj.echo3.ng.able.Positionable.PROPERTY_LEFT;
import static com.cachat.prj.echo3.ng.able.Positionable.PROPERTY_POSITION;
import static com.cachat.prj.echo3.ng.able.Positionable.PROPERTY_RIGHT;
import static com.cachat.prj.echo3.ng.able.Positionable.PROPERTY_TOP;
import static com.cachat.prj.echo3.ng.able.Positionable.PROPERTY_Z_INDEX;
import com.cachat.prj.echo3.ng.able.Sizeable;
import static com.cachat.prj.echo3.ng.able.Widthable.PROPERTY_WIDTH;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;

/**
 *
 * @author scachat
 */
public class Synoptique extends Component implements Positionable, Sizeable {

    /*package protected*/ static final Logger logger = Logger.getLogger(Synoptique.class.getName());
    /**
     * evenement modification d'un objet
     */
    public static final String OBJECT_EDIT = "objectEdit";
    /**
     * evenement clic sur un objet
     */
    public static final String OBJECT_CLIC = "objectClic";
    /**
    /**
     * evenement clic sur un objet
     */
    public static final String FULL_UPDATE = "fullUpdate";
    /**
     * propriete mise à jour du synoptique
     */
    public static final String ACTION = "action";
    /**
     * propriete etat du synoptique
     */
    public static final String STATE = "state";
    /**
     * les objets de ce synoptique
     */
    private Map<String, SynObject> objects = new HashMap<>();

    /**
     * prochaines actions a envoyer
     */
    private SynAction action = null;
    /**
     * verrou pour l'accès à action
     */
    private ReentrantLock actionLock = new ReentrantLock();
    /**
     * map des uid vers les objets ayant un listener clic
     */
    private Map<String, SynObject> objectWithClicListener = new HashMap<>();
    /**
     * map des uid vers les objets ayant un listener modification
     */
    private Map<String, SynObject> objectWithEditListener = new HashMap<>();

    /**
     * les vues utilisées par ce synoptique
     */
    private Map<String, SynView> views = new HashMap<>();

    public Synoptique() {
    }

    @Override
    public void init() {
        super.init();
        SynManager.getInstance().register(this);
    }

    @Override
    public void dispose() {
        super.dispose();
        SynManager.getInstance().unregister(this);
    }

    /**
     * ajoute un groupe d'objets au synoptique
     *
     * @param obj l'objet
     */
    public void add(SynGroupe obj) {
        obj.addTo(this);
    }

    /**
     * supprime un groupe d'objets au synoptique
     *
     * @param obj l'objet
     */
    public void remove(SynGroupe obj) {
        obj.removeFrom(this);
    }

    /**
     * ajoute un objet au synoptique
     *
     * @param obj l'objet
     */
    public void add(SynObject obj) {
        actionLock.lock();
        try {
            objects.put(obj.getUid(), obj);
            if (action == null) {
                action = new SynAction();
            }
            action.getAdd().add(obj);
            if (obj.hasClicListener()) {
                objectWithClicListener.put(obj.getUid(), obj);
            }
            if (obj.hasEditListener()) {
                objectWithEditListener.put(obj.getUid(), obj);
            }
            SynView view = obj.getView();
            if (view != null && !(view instanceof SynViewBasic)) {
                logger.severe("Store view " + view.getClass().getSimpleName() + " " + view.getUid() + " for object " + obj.getUid());
                views.put(view.getUid(), view);
            }
            obj.setSynoptique(this);
        } finally {
            actionLock.unlock();
        }
        if (getApplicationInstance() != null) {
            firePropertyChange(ACTION, null, action);
        }
    }

    /**
     * enregistre une nouvelle vue (par exemple si un objet avait une vue,
     * enregistrée lors de l'ajout, puis qu'on change sa vue
     *
     * @param obj l'objet
     * @param view la nouvelle vue
     */
    public void registerNewView(SynObject obj, SynView view) {
        if (view != null && !(view instanceof SynViewBasic)) {
            logger.severe("Store view " + view.getClass().getSimpleName() + " " + view.getUid() + " for object " + obj.getUid());
            views.put(view.getUid(), view);
        }
    }

    /**
     * modifie un objet
     *
     * @param obj l'objet
     */
    public void update(SynObject obj) {
        actionLock.lock();
        try {
            if (action == null) {
                action = new SynAction();
                action.setFull(true);
                action.getAdd().addAll(objects.values());
            }
            action.getUpdate().add(obj);
            if (obj.hasClicListener()) {
                objectWithClicListener.put(obj.getUid(), obj);
            }
            if (obj.hasEditListener()) {
                objectWithEditListener.put(obj.getUid(), obj);
            }
            //TODO handle view delete/change
        } finally {
            actionLock.unlock();
        }
        if (getApplicationInstance() != null) {
            firePropertyChange(ACTION, null, action);
        }
    }

    /**
     * supprime un objet
     *
     * @param obj l'objet
     */
    public void remove(SynObject obj) {
        actionLock.lock();
        try {
            objects.remove(obj.getUid());
            objectWithClicListener.remove(obj.getUid());
            objectWithEditListener.remove(obj.getUid());
            if (action == null) {
                action = new SynAction();
            }
            //TODO handle view delete
            action.getDel().add(obj.getUid());
            obj.setSynoptique(null);
        } finally {
            actionLock.unlock();
        }
        if (getApplicationInstance() != null) {
            firePropertyChange(ACTION, null, action);
        }
    }

    /**
     * donne la vue
     *
     * @param uid l'uid de la vue demandée
     * @return la vue ou null
     */
    public SynView getView(String uid) {
        return views.get(uid);
    }

    /**
     * donne l'action en cours et la remet à zéro
     *
     * @return l'action
     */
    /*package protected */ SynAction getActionAndClear() {
        actionLock.lock();
        try {
            if (action == null) {
                logger.info("Send empty Action");
                return new SynAction();
            } else {
                logger.info("Send and clear Action");
                SynAction a = action;
                action = null;
                return a;
            }

        } finally {
            actionLock.unlock();
        }
    }

    /**
     * donne l'action en cours
     *
     * @return
     */
    public SynAction getAction() {
        return action;
    }

    public void setAction(SynAction action) {
        this.action = action;
    }

    /**
     * donne l'état en cours
     *
     * @return
     */
    public Collection<SynObject> getState() {
        return objects.values();
    }

    public void setState(Collection<SynObject> state) {

    }

    /**
     * supprime tous les objets
     */
    @Override
    public void clear() {
        actionLock.lock();
        try {
            if (action == null) {
                action = new SynAction();
            }
            objects.values().forEach(obj -> {
                action.getDel().add(obj.getUid());
            });
            objects.clear();
            objectWithClicListener.clear();
            objectWithEditListener.clear();
        } finally {
            actionLock.unlock();
        }
        firePropertyChange(ACTION, action, null);
    }

    /**
     * donne un objet à partir de son uid
     *
     * @param uid l'uid
     * @return l'objet ou null
     */
    public SynObject getObject(String uid) {
        return objects.get(uid);
    }

    /**
     * Fixe les limites. position sera absolute
     *
     * @param left extent en pixel, ou null si indéfini
     * @param top extent en pixel, ou null si indéfini
     * @param right extent en pixel, ou null si indéfini
     * @param bottom extent en pixel, ou null si indéfini
     * @param width extent en pixel, ou null si indéfini
     * @param height extent en pixel, ou null si indéfini
     */
    public void setBounds(Integer left, Integer top, Integer right, Integer bottom, Integer width, Integer height) {
        setPosition(Positionable.ABSOLUTE);
        if (top != null) {
            setTop(new Extent(top));
        }
        if (left != null) {
            setLeft(new Extent(left));
        }
        if (right != null) {
            setRight(new Extent(right));
        }
        if (bottom != null) {
            setBottom(new Extent(bottom));
        }
        if (width != null) {
            setWidth(new Extent(width));
        }
        if (height != null) {
            setHeight(new Extent(height));
        }
    }

    @Override
    public Extent getBottom() {
        return (Extent) get(PROPERTY_BOTTOM);
    }

    @Override
    public Extent getLeft() {
        return (Extent) get(PROPERTY_LEFT);
    }

    @Override
    public int getPosition() {
        return (Integer) get(PROPERTY_POSITION);
    }

    @Override
    public Extent getRight() {
        return (Extent) get(PROPERTY_RIGHT);
    }

    @Override
    public Extent getTop() {
        return (Extent) get(PROPERTY_TOP);
    }

    @Override
    public int getZIndex() {
        return (Integer) get(PROPERTY_Z_INDEX);
    }

    @Override
    public boolean isPositioned() {
        return getPosition() != Positionable.STATIC;
    }

    @Override
    public void setPosition(int newValue) {
        set(PROPERTY_POSITION, newValue);
    }

    @Override
    public void setBottom(Extent newValue) {
        set(PROPERTY_BOTTOM, newValue);
    }

    @Override
    public void setLeft(Extent newValue) {
        set(PROPERTY_LEFT, newValue);
    }

    @Override
    public void setRight(Extent newValue) {
        set(PROPERTY_RIGHT, newValue);
    }

    @Override
    public void setTop(Extent newValue) {
        set(PROPERTY_TOP, newValue);
    }

    public void setBottom(int newValue) {
        set(PROPERTY_BOTTOM, new Extent(newValue));
    }

    public void setLeft(int newValue) {
        set(PROPERTY_LEFT, new Extent(newValue));
    }

    public void setRight(int newValue) {
        set(PROPERTY_RIGHT, new Extent(newValue));
    }

    public void setTop(int newValue) {
        set(PROPERTY_TOP, new Extent(newValue));
    }

    @Override
    public Extent getWidth() {
        return (Extent) get(PROPERTY_WIDTH);
    }

    @Override
    public void setWidth(Extent newValue) {
        set(PROPERTY_WIDTH, newValue);
    }

    public void setWidth(int newValue) {
        set(PROPERTY_WIDTH, new Extent(newValue));
    }

    @Override
    public Extent getHeight() {
        return (Extent) get(PROPERTY_HEIGHT);
    }

    @Override
    public void setHeight(Extent newValue) {
        set(PROPERTY_HEIGHT, newValue);
    }

    public void setHeight(int newValue) {
        set(PROPERTY_HEIGHT, new Extent(newValue));
    }

    @Override
    public void setZIndex(int newValue) {
        set(PROPERTY_Z_INDEX, newValue);
    }

    @Override
    public void processInput(String inputName, Object inputValue) {
        super.processInput(inputName, inputValue);
        System.err.printf("EVENT %s => %s\r\n", inputName, inputValue);
        switch (inputName) {
            case OBJECT_EDIT -> {
                SynModifiedEvent sme = (SynModifiedEvent) inputValue;
                SynObject obj = objects.get(sme.getUid());
                if (obj != null) {
                    obj.edit(sme);
                }
            }
            case OBJECT_CLIC -> {
                SynClicEvent sce = (SynClicEvent) inputValue;
                SynObject obj = objects.get(sce.getUid());
                if (obj != null) {
                    obj.clic(sce);
                }
            }
            case FULL_UPDATE -> {
                if (actionLock.tryLock()) {
                    try {
                        action = new SynAction();

                    } finally {
                        actionLock.unlock();
                    }
                }
            }
            default ->
                logger.severe("unhandled input value " + inputName + " = " + inputValue);
        }
    }

    /*package protected*/ boolean hasClicListeners() {
        return !objectWithClicListener.isEmpty();
    }

    /*package protected*/ boolean hasEditListeners() {
        return !objectWithEditListener.isEmpty();
    }

}
