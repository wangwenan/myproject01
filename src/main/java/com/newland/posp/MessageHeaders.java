package com.newland.posp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class MessageHeaders implements Map<String, Object>, Serializable {

	private static final long serialVersionUID = 7991964408513271543L;

	private static final Log logger = LogFactory.getLog(MessageHeaders.class);

	private static final String PREFIX = "newland.posp.";

	public static final String ID = PREFIX + "id";

	public static final String TIMESTAMP = PREFIX + "timestamp";

	public static final String CORRELATION_ID = PREFIX + "correlationId";

	public static final String REPLY_CHANNEL = PREFIX + "replyChannel";

	public static final String ERROR_CHANNEL = PREFIX + "errorChannel";

	public static final String EXPIRATION_DATE = PREFIX + "expirationDate";

	public static final String PRIORITY = PREFIX + "priority";

	public static final String SEQUENCE_NUMBER = PREFIX + "sequenceNumber";

	public static final String SEQUENCE_SIZE = PREFIX + "sequenceSize";
	
	public static final String CHARSET = PREFIX + "charset";


	private final Map<String, Object> headers;


	public MessageHeaders(Map<String, Object> headers) {
		this.headers = (headers != null)
				? new HashMap<String, Object>(headers)
				: new HashMap<String, Object>();
		this.headers.put(ID, UUID.randomUUID());
		this.headers.put(TIMESTAMP, new Long(System.currentTimeMillis()));
	}


	public Object getId() {
		return this.get(ID);
	}

	public Long getTimestamp() {
		return this.get(TIMESTAMP, Long.class);
	}

	public Long getExpirationDate() {
		return this.get(EXPIRATION_DATE, Long.class);
	}

	public Object getCorrelationId() {
		return this.get(CORRELATION_ID);
	}

	public Object getReplyChannel() {
		return this.get(REPLY_CHANNEL);
	}

	public Object getErrorChannel() {
		return this.get(ERROR_CHANNEL);
	}

	public Integer getSequenceNumber() {
		Integer sequenceNumber = this.get(SEQUENCE_NUMBER, Integer.class);
		return (sequenceNumber != null ? sequenceNumber : 0);
	}

	public Integer getSequenceSize() {
		Integer sequenceSize = this.get(SEQUENCE_SIZE, Integer.class);
		return (sequenceSize != null ? sequenceSize : 0);
	}

	public MessagePriority getPriority() {
		return this.get(PRIORITY, MessagePriority.class);
	}

	public <T> T get(Object key, Class<T> type) {
		Object value = this.headers.get(key);
		if (value == null) {
			return null;
		}
		if (!type.isAssignableFrom(value.getClass())) {
			throw new IllegalArgumentException("Incorrect type specified for header '" + key
					+ "'. Expected [" + type + "] but actual type is [" + value.getClass() + "]");
		}
		return (T) value;
	}

	public int hashCode() {
		return this.headers.hashCode();
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj != null && obj instanceof MessageHeaders) {
			MessageHeaders other = (MessageHeaders) obj;
			return this.headers.equals(other.headers);
		}
		return false;
	}

	public String toString() {
		return this.headers.toString();
	}

	/*
	 * Map implementation
	 */

	public boolean containsKey(Object key) {
		return this.headers.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return this.headers.containsValue(value);
	}

	public Set<Map.Entry<String, Object>> entrySet() {
		return Collections.unmodifiableSet(this.headers.entrySet());
	}

	public Object get(Object key) {
		return this.headers.get(key);
	}

	public boolean isEmpty() {
		return this.headers.isEmpty();
	}

	public Set<String> keySet() {
		return Collections.unmodifiableSet(this.headers.keySet());
	}

	public int size() {
		return this.headers.size();
	}

	public Collection<Object> values() {
		return Collections.unmodifiableCollection(this.headers.values());
	}

	/*
	 * Unsupported operations
	 */

	public Object put(String key, Object value) {
		throw new UnsupportedOperationException("MessageHeaders is immutable.");
	}

	public void putAll(Map<? extends String, ? extends Object> t) {
		throw new UnsupportedOperationException("MessageHeaders is immutable.");
	}

	public Object remove(Object key) {
		throw new UnsupportedOperationException("MessageHeaders is immutable.");
	}

	public void clear() {
		throw new UnsupportedOperationException("MessageHeaders is immutable.");
	}

	/*
	 * Serialization methods
	 */

	private void writeObject(ObjectOutputStream out) throws IOException {
		List<String> keysToRemove = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : this.headers.entrySet()) {
			if (!(entry.getValue() instanceof Serializable)) {
				keysToRemove.add(entry.getKey());
			}
		}
		for (String key : keysToRemove) {
			if (logger.isInfoEnabled()) {
				logger.info("removing non-serializable header: " + key);
			}
			this.headers.remove(key);
		}
		out.defaultWriteObject();
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
	}

}
