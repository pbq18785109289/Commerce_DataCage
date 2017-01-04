package com.dhcc.datacage.client;

import org.jivesoftware.smack.packet.IQ;

/**
 * 消息收发确认的IQ
 * @author pengbangqin
 *
 */
public class DeliverConfirmIQ extends IQ {
	String uuid;

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String getChildElementXML() {
		StringBuilder buf = new StringBuilder();
		buf.append("<").append("deliverconfirm").append(" xmlns=\"").append(
				"androidpn:iq:deliverconfirm").append("\">");
		if (uuid != null) {
			buf.append("<uuid>").append(uuid).append("</uuid>");
		}
		buf.append("</").append("deliverconfirm").append("> ");
		return buf.toString();
	}

}

