package dev.iseal.sealparticleplayer.client.Listeners;

import com.esotericsoftware.kryo.kryo5.io.Input;
import dev.iseal.ExtraKryoCodecs.Enums.Effekt;
import dev.iseal.sealparticleplayer.client.HFPPClient;
import dev.iseal.sealparticleplayer.client.Handlers.EffektHandler;
import dev.iseal.sealparticleplayer.client.UnsafeSerializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

public class ClientNetworkingListener {

    public void initialize() {
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(HFPPClient.MODID, "effekts"), (client, handler, buf, responseSender) -> {
            byte[] array = buf.getWrittenBytes();
            System.out.println("Received data array: " + Arrays.toString(array));
            System.out.println("Received data array length: " + array.length);
            byte[] indexMoved = Arrays.copyOfRange(array, 4, array.length);
            try {
                UnsafeSerializer.checkKryo();
                ByteArrayInputStream inputStream = new ByteArrayInputStream(indexMoved);
                Input input = new Input(inputStream);
                Effekt effekt = (Effekt) UnsafeSerializer.kryo.readObject(input, Effekt.class);
                System.out.println("Read enum constant: "+effekt);
                EffektHandler.handleEffekt(UnsafeSerializer.kryo.readObject(input, effekt.getEffectClass()), effekt);
            } catch (Exception e) {
                // assume that the exception is caused by incorrect packet
                System.out.println("Failed to deserialize packet: "+e.getMessage());
            }
        });
    }

}
