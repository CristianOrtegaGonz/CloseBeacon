package se.grouprich.closebeacon.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import se.grouprich.closebeacon.requestresponsemanager.bytearraybuilder.ByteArrayBuilder;

public final class BeaconActivationCommand {

    private byte[] sha1Hash;
    private byte[] companyId;
    private byte id1;
    private byte id2;
    private byte[] proximityUuidByteArray;
    private byte[] majorNumber;
    private byte[] minorNumber;
    private byte power;
    private byte[] filling;

    public BeaconActivationCommand(byte[] sha1Hash, String proximityUuidString, String majorNumberString, String minorNumberString) {

        this.sha1Hash = sha1Hash;
        companyId = ByteBuffer.allocate(2).put((byte) 0x004C).array();
        id1 = 2;
        id2 = 21;

        UUID proximityUuid = UUID.fromString(proximityUuidString);
        proximityUuidByteArray = ByteBuffer.allocate(16)
                .putLong(proximityUuid.getMostSignificantBits())
                .putLong(proximityUuid.getLeastSignificantBits())
                .array();

        this.majorNumber = ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN)
                .put(ByteArrayBuilder.numberStringToByteArray(majorNumberString))
                .array();

        this.minorNumber = ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN)
                .put(ByteArrayBuilder.numberStringToByteArray(minorNumberString))
                .array();

        power = (byte) 0xC5;

        filling = new byte[46];
        Arrays.fill(filling, (byte) 0);
    }

    public byte[] buildByteArray() {

        return ByteBuffer.allocate(91)
                .put(sha1Hash)
                .put(companyId)
                .put(id1)
                .put(id2)
                .put(proximityUuidByteArray)
                .put(majorNumber)
                .put(minorNumber)
                .put(power)
                .put(filling)
                .array();
    }
}
