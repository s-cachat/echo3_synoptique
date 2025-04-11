package com.cachat.prj.echo3.synoptique;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import nextapp.echo.app.serial.SerialException;
import nextapp.echo.app.serial.SerialPropertyPeer;
import nextapp.echo.app.util.Context;
import org.w3c.dom.Element;

/**
 *
 * @author scachat
 */
public class SynActionPeer implements SerialPropertyPeer {

    @Override
    public Object toProperty(Context context, Class objectClass, Element propertyElement) throws SerialException {
        SynClicEvent x = new SynClicEvent();
        x.setUid(propertyElement.getAttribute("uid"));
        return x;
    }

    @Override
    public void toXml(Context context, Class objectClass, Element propertyElement, Object propertyValue) throws SerialException {
        propertyElement.setAttribute("t", "SynAction");
        try {
            JAXBContext jc = JAXBContext.newInstance(SynAction.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.marshal(propertyValue, propertyElement);
        } catch (JAXBException ex) {
            throw new SerialException("", ex);
        }
    }
}
