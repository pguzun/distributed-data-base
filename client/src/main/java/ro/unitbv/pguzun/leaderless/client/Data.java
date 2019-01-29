package ro.unitbv.pguzun.leaderless.client;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class Data {
    @JsonProperty("version")
    private final long version;

    @JsonProperty("value")
    private final byte[] value;

    @JsonCreator
    public Data(@JsonProperty("version") long version, @JsonProperty("value") byte[] value) {
        this.version = version;
        this.value = value;
    }

    public Data(byte[] value) {
        this(value.hashCode(), value);
    }

    public long getVersion() {
        return version;
    }

    public byte[] getValue() {
        return value;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("version", version).add("value", value).toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(version);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Data)) {
            return false;
        }

        Data other = (Data) obj;
        return version == other.version;
    }
}
