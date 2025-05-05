/* fuck  */
Synoptique = {};
Synoptique = Core.extend(Echo.Component, {
    $load: function () {
        Echo.ComponentFactory.registerType("Synoptique", this);
        console.log("Synoptique load : ", this.toString(1));
    },
    componentType: "Synoptique"
});
/**
 * Date Property Translator Singleton.
 */
Synoptique.SerialEvent = Core.extend(Echo.Serial.PropertyTranslator, {

    $static: {
        /** @see Echo.Serial.PropertyTranslator#toProperty */
        toProperty: function (client, pElement) {
            return {};
        },
        /** @see Echo.Serial.PropertyTranslator#toXml */
        toXml: function (client, propertyElement, propertyValue) {
            for (const property in propertyValue) {
                var value = propertyValue[property];
                if (value) {
                    console.log("Event  ", property, " => ", value);
                    propertyElement.setAttribute(property, value);
                }
            }
        }
    },
    $load: function () {
        Echo.Serial.addPropertyTranslator("SynModifiedEvent", this);
    }
});

Synoptique.Sync = Core.extend(Echo.Render.ComponentSync, {
    $load: function () {
        Echo.Render.registerPeer("Synoptique", this);
        componentType : "Synoptique";
    },
    _div: null,
    _canvas: null,
    _fabric: null,
    _disposed: false,
    _content: {},
    _content2: {},
    /**
     * met à jour les proprietes generiques d'un objet
     * @param {type} action les proprietes
     * @return true si setCoord doit être appelé
     */
    updateObj(action) {
        console.log("updateObj", this, action);
        var setCoord = false;
        if (action.angle && this.angle !== action.angle) {
            this.angle = action.angle;
            setCoord = true;
        }
        if (action.top && this.top !== action.top) {
            this.top = action.top;
            setCoord = true;
        }
        if (action.zindex) {
            this.zIndex = action.zindex;
        }
        if (action.left && this.left !== action.left) {
            this.left = action.left;
            setCoord = true;
        }
        var hasControl = undefined;
        var selectable = undefined;
        var clickable = undefined;
        if (action.movable !== undefined) {
            if (action.movable) {
                this.lockMovementX = false;
                this.lockMovementY = false;
                this.lockRotation = false;
                hasControl = true;
            } else {
                this.lockMovementX = true;
                this.lockMovementY = true;
                this.lockRotation = true;
                hasControl = false;
            }
        }
        if (action.resizeable !== undefined) {
            if (action.resizeable) {
                this.lockScalingX = false;
                this.lockScalingY = false;
                hasControl = true;
            } else {
                this.lockScalingX = true;
                this.lockScalingY = true;
                if (hasControl === undefined) {
                    hasControl = false;
                }
            }
        }
        this.hasBorders = hasControl;
        this.hasControls = hasControl;
        selectable = hasControl;
        var _obj = this;
        if (hasControl !== undefined) {
            if (hasControl) {
                if (this.modifiedHandler) {
                } else {
                    var handler = function (e) {
                        _obj.syn._modifiedEvent(_obj, e);
                    };
                    this.modifiedHandler = handler;
                    this.on("modified", handler);
                }
            } else {
                if (this.modifiedHandler) {
                    this.off("modified", this.modifiedHandler);
                    this.modifiedHandler = undefined;
                }

            }
        }
        if (action.clickable !== undefined) {
            if (action.clickable) {
                if (this.clickHandler) {
                } else {
                    var handler = function (e) {
                        _obj.syn._clicEvent(_obj, e);
                    };
                    this.clickHandler = handler;
                    this.on("mouseup", handler);
                    selectable = true;
                    clickable = true;
                }
            } else {
                if (this.clickHandler) {
                    this.off("mouseup", this.clickHandler);
                    this.clickHandler = undefined;
                }
                if (selectable === undefined) {
                    selectable = false;
                    clickable = false;
                }
            }
        }
        console.log("selectable ", selectable, "clickable", clickable, this);
        if (selectable !== undefined) {
            this.set('selectable', selectable);
        }


        return setCoord;
    },
    /**
     * met à jour les proprietes d'une forme
     * @param {type} action les proprietes
     */
    updateShape(action) {
        if (action.coord) {
            try {
                action._coord = JSON.parse(action.coord).values;
            } catch (e) {
                action._coord = [];
                console.log("Can't parse ", action.coord);
            }
        }
        var setCoord = this.updateObj(action);
        if (setCoord) {
            console.log("setCoord for ", this.renderId);
            this.setCoords();
        }
        if (action.fillColor) {
            this.fill = "#" + action.fillColor.toString(16).padStart(6, '0');
        }
        if (action.stroke) {
            this.stroke = "#" + action.stroke.toString(16).padStart(6, '0');
        }
        if (action.strokeWidth) {
            this.strokeWidth = action.strokeWidth;
        }
    },
    /**
     * met à jour les proprietes d'un rectangle
     * @param {type} action les proprietes
     */
    updateRect(action) {
        this.updateShape(action);
        if (action._coord.length >= 2) {
            this.width = action._coord[0];
            this.height = action._coord[1];
            console.log("updateCircle width", this.width, " height", this.height);
            this.setCoords();
        }
    },
    /**
     * met à jour les proprietes d'un rectangle
     * @param {type} action les proprietes
     */
    updateCircle(action) {
        this.updateShape(action);

        if (action._coord.length >= 1) {
            this.radius = action._coord[1];
            if (action._coord.length >= 3) {
                this.startAngle = action._coord[1];
                this.endAngle = action._coord[2];
            }
            console.log("updateCircle radius", this.radius, " startAngle", this.startAngle, " endAngle", this.endAngle);
            this.setCoords();
        }
    },
    /**
     * met à jour les proprietes d'un texte
     * @param {type} action les proprietes
     */
    updateText(action) {
        var setCoord = this.updateObj(action);
        if (setCoord) {
            console.log("setCoord for ", this.renderId);
            this.setCoords();
        }
        if (action.text) {
            this.set('text', action.text);
        }
        if (action.fontSize) {
            this.set('fontSize', action.fontSize);
        }
        if (action.underline) {
            this.set('underline', action.underline);
        }
        if (action.linethrough) {
            this.set('linethrough', action.linethrough);
        }
        if (action.overline) {
            this.set('overline', action.overline);
        }
        if (action.fontStyle) {
            this.set('fontStyle', action.fontStyle);
        }
        if (action.fontFamily) {
            this.set('fontFamily', action.fontFamily);
        }
        if (action.textAlign) {
            this.set('textAlign', action.textAlign);
        }
        if (action.textBackgroundColor) {
            this.set('textBackgroundColor', "#" + action.textBackgroundColor.toString(16).padStart(6, '0'));
        }

    },
    _reorderObjects() {
        this._fabric._objects.sort(function (a, b) {
            return (b.zIndex < a.zIndex) ? 1 : ((b.zIndex > a.zIndex) ? -1 : 0);
        });
        this._fabric.renderAll();
    },
    renderDisplay: function () {
        console.log("renderDisplay *************");
        const _this = this;
        if (this._fabric === null) {
            this._canvas.width = this._div.offsetWidth;
            this._canvas.height = this._div.offsetHeight;
            this._fabric = new fabric.Canvas(this._canvas);
            try {
                this._fabric.on('mouse:wheel', function (opt) {
                    var delta = opt.e.deltaY;
                    var zoom = _this._fabric.getZoom();
                    zoom *= 0.999 ** delta;
                    if (zoom > 20)
                        zoom = 20;
                    if (zoom < 0.01)
                        zoom = 0.01;
                    _this._fabric.setZoom(zoom);
                    opt.e.preventDefault();
                    opt.e.stopPropagation();
                });

            } catch (e) {
                console.log(e);
            }
            for (var i = 0; i < this.futureAdd.length; i++) {
                this._renderAddChild(this.futureAdd[i]);
            }
            this.futureAdd = [];
        }
    },
    _objectPostCreate(action, obj) {
        this._fabric.add(obj);
        this._content[obj.renderId] = obj;
        this._content2[obj.renderId] = obj;
        this.lastObj = obj;
        obj._updateObj(action);
        this._reorderObjects();
    },
    _modifiedEvent(source, event) {
        var mEvent = {
            className: "SynModifiedEvent",
            left: source.left,
            top: source.top,
            width: source.width,
            height: source.height,
            angle: source.angle,
            uid: source.renderId
        };
        this.component.fireEvent({
            type: 'objectEdit',
            source: this.component,
            data: mEvent
        });
    },
    _clicEvent(source, event) {
        var cEvent = {
            className: "SynModifiedEvent",
            left: source.left,
            top: source.top,
            width: source.width,
            height: source.height,
            angle: source.angle,
            uid: source.renderId,
            mouseX: event.pointer.x,
            mouseY: event.pointer.y,
            screenX: event.screenX,
            screenY: event.screenY
        };
        console.log("click source", source, " mEvent", cEvent, " event ", event, "component", this.component);
        this.component.fireEvent({
            type: 'objectClic',
            source: this.component,
            data: cEvent
        });
    },
    renderAdd: function (update, parentElement) {
        console.log("render add update", update, ", action", this.component.get("action"));
        console.log("Synoptique renderAdd this is ", this);
        console.log("Synoptique ID ==========================> ", this.component.renderId);
        if (this._div === null) {
            this._div = document.createElement("div");
            this._div.id = this.component.renderId;
            Echo.Sync.renderComponentDefaults(this.component, this._div);
            Extended.renderPositionnable(this.component, this._div);
            parentElement.appendChild(this._div);
        }
        if (this._canvas === null) {
            this._canvas = document.createElement("canvas");
            this._canvas.id = this.component.renderId + "_canvas";
            this._div.appendChild(this._canvas);
        }

        var componentCount = this.component.getComponentCount();
        this.futureAdd = [];
        for (var i = 0; i < componentCount; i++) {
            var child = this.component.getComponent(i);
            this.futureAdd[i] = child;
            child.futureAdd = "futureAdd";
        }
    },
    renderDispose: function (update) {
        this._disposed = true;
    },
    scan: function (item) {
        var res = item.renderId;
        var first = true;
        if (item.children) {
            for (i = 0; i < item.children.length; i++) {
                if (first) {
                    res = res + "(";
                    first = false;
                } else {
                    res = res + ", ";
                }
                res = res + this.scan(item.children[i])
            }
        } else {
            console.log("no child", item);
            res = res + "*";
        }
        if (!first) {
            res = res + ")";
        }
        return res;
    },
    _renderAddChild: function (x) {
        x._listenerList = {
            hasListeners: function (type) {
                return "property" === type;
            },
            fireEvent: function (e) {
                if (x.obj) {
                    x.obj.updateProperty(e.propertyName, e.newValue);

                }
            }
        };
        var obj = undefined;
        if (x.componentType) {
            switch (x.componentType) {
                case "SynShape":
                    {
                        var coord;
                        if (x._localStyle.coord) {
                            coord = JSON.parse(x._localStyle.coord).values;
                        } else {
                            coord = [];
                        }
                        switch (x._localStyle.type) {
                            case "RECT":
                                {
                                    obj = new fabric.Rect({width: coord[0], height: coord[1]});
                                    obj._updateObj = this.updateRect;
                                }
                                break;
                            case "CIRCLE":
                                {
                                    var param = {
                                        radius: coord[0]
                                    };
                                    obj = new fabric.Circle(param);
                                    obj._updateObj = this.updateCircle;
                                }
                                break;
                            default:
                                {
                                    console.log("unsupported shape ", x._localStyle.type);
                                }
                                break;
                        }
                        if (obj !== undefined) {
                            obj.updateShape = this.updateShape;
                        }
                    }
                    break;

                case "SynText":
                    {
                        obj = new fabric.Text("TEST", {fontSize: 10});
                        obj._updateObj = this.updateText;
                    }
                    break;
                case "SynImage":
                    {
                        console.log("add image", x);
                        var _syn = this;
                        var _renderId = this.component.renderId;
                        var url = "synView/" + _renderId + "/" + x._localStyle.viewId + "/view.svg";
                        console.log("create image view ", " url:", url, " action:", x.localStyle);
                        fabric.Image.fromURL(url, {"left": x._localStyle.left, "top": x._localStyle.top, "height": x._localStyle.height, "width": x._localStyle.width})
                                .then(function (nobj) {
                                    console.log("image style", x._localStyle);
                                    nobj.view = {
                                        uid: x._localStyle.viewId,
                                        renderId: _renderId,
                                        file: "view.jpg",
                                        height: x._localStyle.height,
                                        width: x._localStyle.width
                                    };
                                    nobj.zIndex = x._localStyle.zindex === undefined ? 100 : x._localStyle.zindex;

                                    nobj.scaleToFit = function () {
                                        this.view.scaleX = this.view.width / this._originalElement.width;
                                        this.view.scaleY = this.view.height / this._originalElement.height;
                                        this.set('scaleX', this.view.scaleX);
                                        this.set('scaleY', this.view.scaleY);
                                        console.log(" scaling image from", this._originalElement.width, "x", this._originalElement.height, " to ", this.view.width, "x", this.view.height, " : ", this);
                                    };
                                    nobj.syn = _syn;
                                    nobj._updateObj = _syn.updateObj;
                                    nobj.view = x._localStyle;
                                    nobj.renderId = x.renderId;
                                    _syn._objectPostCreate(x._localStyle, nobj);
                                    x.obj = nobj;
                                    x.obj.scaleToFit();
                                    _syn._fabric.renderAll();
                                    x.obj.updateProperty = function (k, v) {
                                        if (k === "viewId") {
                                            var url = "synView/" + _renderId + "/" + v + "/view.svg";
                                            x.obj.setSrc(url).then(function (img) {
                                                x.obj.setCoords();
                                                x.obj.syn._fabric.renderAll();
                                            });
                                            console.log("*update url of ", x.obj.renderId, " to ", url);
                                        } else {
                                            console.log("*update property ", k, " of ", x.obj.renderId, " to ", v);
                                        }
                                        x.obj.set(k, v);
                                        x.obj.scaleToFit();
                                        x.obj.syn._fabric.renderAll();
                                    };
                                });
                    }
                    break;
                default:
                    {
                        console.log("unsupported child \"", x.componentType, "\"");
                    }
                    break;
            }
            if (obj !== undefined) {
                obj.updateObj = this.updateObj;
                obj.view = x._localStyle;
                obj.renderId = x.renderId;
                obj.updateProperty = function (k, v) {
                    console.log("update property ", k, " of ", obj.renderId, " to ", v);
                    obj.set(k, v);
                    obj.syn._fabric.renderAll();
                };
                this._objectPostCreate(x._localStyle, obj);
            }
        }
        if (obj !== undefined) {
            obj.syn = this;
            x.obj = obj;
        }
    },
    renderUpdate: function (update) {
        console.log("Synoptique renderUpdate ", update, " action", this.component.get("action"));
        console.log(update);
        var addedChildren = update.getAddedChildren();
        if (addedChildren) {
            for (i = 0; i < addedChildren.length; ++i) {
                this._renderAddChild(addedChildren[i]);
            }
        }
        var removedChildren = update.getRemovedChildren();
        if (removedChildren) {
            for (i = 0; i < removedChildren.length; ++i) {
                console.log("Synoptique render remove child => ", " TREE ", this.scan(removedChildren[i]));
                //this._renderRemoveChild(update, removedChildren[i]);
            }
        }
        var updatedChildren = update.getUpdatedLayoutDataChildren();
        if (updatedChildren) {
            for (i = 0; i < updatedChildren.length; ++i) {
                console.log("Synoptique render update child => ", updatedChildren[i], " TREE ", this.scan(updatedChildren[i]));
                //this._renderRemoveChild(update, updatedChildren[i]);
            }
        }
        var pu = update._propertyUpdates;
        if (pu) {
            console.log("pu", pu);
        } else {

        }
        return true;
    }
}
);


SynObject = {};
SynObject = Core.extend(Echo.Component, {
    $load: function () {
        Echo.ComponentFactory.registerType("SynObject", this);
    }
});

SynObject.Sync = Core.extend(Echo.Render.ComponentSync, {
    $load: function () {
        Echo.Render.registerPeer("SynObject", this);
        componentType : "SynObject";
    },
    _parent: null,
    _left: null,
    _top: null,
    _angle: null,
    _width: null,
    _height: null,
    _zIndex: null,
    _clickable: null,
    _movable: null,
    _resizeable: null,
    _id: null,
    renderAdd: function (update, parentElement) {
        this._parent = parentElement;
        this._id = this.component.renderId;
        this._left = this.component.render("left");
        this._top = this.component.render("top");
        this._angle = this.component.render("angle");
        this._width = this.component.render("width");
        this._height = this.component.render("height");
        this._zIndex = this.component.render("zIndex");
        this._clickable = this.component.render("clickable");
        this._movable = this.component.render("movable");
        this._resizeable = this.component.render("resizeable");
        console.log("SynObject " + this._id + " renderAdd ", update, " => ", this);

    },
    renderDispose: function (update) {
        console.log("SynObject " + this._id + " renderDispose ", this);
        try {
            this._parent.removeSynObject(this._layer);
        } catch (e) {
            //nop
        }
        this._layer = null;
    },
    renderUpdate: function (update) {
        console.log("SynObject " + this._id + " renderUpdate ", update, " => ", this);


        return true;
    }
});
SynText = {};
SynText = Core.extend(SynObject, {
    $load: function () {
        Echo.ComponentFactory.registerType("SynText", this);
    },
    componentType: "SynText"
});

SynText.Sync = Core.extend(SynObject.Sync, {
    $load: function () {
        Echo.Render.registerPeer("SynText", this);
        componentType : "SynText";
    },
    _text: null,
    _fontSize: null,
    renderAdd: function (update, parentElement) {
        this._text = this.component.render("text");
        this._fontSize = this.component.render("fontSize");
        console.log("SynText " + this._id + " renderAdd ", update, " => ", this);
    },
    renderDispose: function (update) {
        console.log("SynText " + this._id + " renderDispose ", this);
        try {
            this._parent.removeSynText(this._layer);
        } catch (e) {
            //nop
        }
        this._layer = null;
    },
    renderUpdate: function (update) {
        console.log("SynText " + this._id + " renderUpdate ", update, " => ", this);
        return true;
    }
});
SynImage = {};
SynImage = Core.extend(SynObject, {
    $load: function () {
        Echo.ComponentFactory.registerType("SynImage", this);
    },
    componentType: "SynImage"
});

SynImage.Sync = Core.extend(SynObject.Sync, {
    $load: function () {
        Echo.Render.registerPeer("SynImage", this);
        componentType : "SynImage";
    },
    _contentType: null,
    _url: null,
    renderAdd: function (update, parentElement) {
        this._contentType = this.component.render("contentType");
        this._url = this.component.render("url");
        console.log("SynImage " + this._id + " renderAdd ", update, " => ", this);
    },
    renderDispose: function (update) {
        console.log("SynImage " + this._id + " renderDispose ", this);
        try {
            this._parent.removeSynImage(this._layer);
        } catch (e) {
            //nop
        }
        this._layer = null;
    },
    renderUpdate: function (update) {
        console.log("SynImage " + this._id + " renderUpdate ", update, " => ", this);


        return true;
    },
    renderAddSyn: function (update) {
        console.log("SynImage renderAddSyn");
    }
});
SynShape = {};
SynShape = Core.extend(SynObject, {
    $load: function () {
        Echo.ComponentFactory.registerType("SynShape", this);
    },
    componentType: "SynShape"
});

SynShape.Sync = Core.extend(SynObject.Sync, {
    $load: function () {
        Echo.Render.registerPeer("SynShape", this);
        componentType : "SynShape";
    },
    _contentType: null,
    _url: null,
    renderAdd: function (update, parentElement) {
        this._contentType = this.component.render("contentType");
        this._url = this.component.render("url");
        console.log("SynShape " + this._id + " renderAdd ", update, " => ", this);
    },
    renderDispose: function (update) {
        console.log("SynShape " + this._id + " renderDispose ", this);
        try {
            this._parent.removeSynShape(this._layer);
        } catch (e) {
            //nop
        }
        this._layer = null;
    },
    renderUpdate: function (update) {
        console.log("SynShape " + this._id + " renderUpdate ", update, " => ", this);
        return true;
    },
    renderAddSyn: function (update) {
        console.log("SynShape renderAddSyn");
    }
});
