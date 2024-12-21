package dev.iseal.sealparticleplayer.client.Listeners;

import dev.iseal.sealparticleplayer.client.Effekts.Effekt;
import dev.iseal.sealparticleplayer.client.HFPPClient;
import dev.iseal.sealparticleplayer.client.UnsafeSerializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;

import java.util.Arrays;

public class ClientNetworkingListener {

    public void initialize() {
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(HFPPClient.MODID, "effekts"), (client, handler, buf, responseSender) -> {
            byte[] array = buf.getWrittenBytes();
            System.out.println("Received data array: " + Arrays.toString(array));
            System.out.println("Received data array length: " + array.length);
            byte[] minus5 = Arrays.copyOfRange(array, 4, array.length-1);
            System.out.println("Received data array minus 5: " + Arrays.toString(minus5));
            System.out.println("old offset: "+buf.readerIndex());
            Effekt readConst = buf.readEnumConstant(Effekt.class);
            System.out.println("Read enum constant: "+readConst);
            // add 4 to the reader index to skip the packet length
            int index = buf.readerIndex()+4;
            byte[] indexMoved = Arrays.copyOfRange(array, index, array.length);
            try {
                ScreenshakeInstance instance = (ScreenshakeInstance) UnsafeSerializer.deserialize(indexMoved, ScreenshakeInstance.class)[0];
                System.out.println("instance "+(instance == null ? "is" : "is not")+" null" );
                System.out.println("TempClass value: " + instance.duration);
            } catch (Exception e) {
                e.printStackTrace();
            }

             /*
            // new new new new
            Effekt effekt = buf.readEnumConstant(Effekt.class);
            Class<?> clazz = effekt.getEffectClass();
            for (Object o : UnsafeSerializer.deserialize(array, Effekt.class, clazz)) {
                System.out.println("Read object: " + o);
            }
            try {
                ScreenshakeHolder instance = (ScreenshakeHolder) UnsafeSerializer.deserialize(array, Effekt.class, clazz)[1];
                System.out.println("Read enum constant: " + effekt);
                System.out.println("instance "+(instance == null ? "is" : "is not")+" null" );
                ScreenshakeInstance easing = (ScreenshakeInstance) instance.easing;
                System.out.println("TempClass value: " + easing);
            } catch (Exception e) {
                e.printStackTrace();
            }

            /*
            // new new new
            System.out.println("Read enum constant: "+buf.readEnumConstant(Effekt.class));
            System.out.println("Read new bytes: "+ Arrays.toString(buf.getWrittenBytes()));

            // new new
            Object[] tempArray = PacketHelper.readInt(array, 4);
            int packetID = (int) tempArray[0];
            byte[] actualBytes = Arrays.copyOfRange(array, (int)tempArray[1], array.length);
            AbstractEffectHolderClass clazz = (AbstractEffectHolderClass) Effect.fromID(packetID).getReferentClass().cast(PacketHelper.deserialize(actualBytes));
            clazz.playEffect();
             */
        });
    }

}
