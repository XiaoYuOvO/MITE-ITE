package net.xiaoyu233.mitemod.miteite.network;

import net.minecraft.*;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SPacketForgingTableInfo extends Packet {
    private String infoClassName;
    private SPacketForgingTableInfo.InfoType info;

    public SPacketForgingTableInfo() {
    }

    public SPacketForgingTableInfo(SPacketForgingTableInfo.InfoType info) {
        this.info = info;
        this.infoClassName = this.info.getClass().getName();
    }

    public SPacketForgingTableInfo.InfoType getInfo() {
        return this.info;
    }

    public int getPacketSize() {
        return 4 + Packet.getPacketSizeOfString(this.infoClassName) + this.info.getDataLength();
    }

    public void processPacket(Connection var1) {
        var1.processForgingTableInfoPacket(this);
    }

    public void readPacketData(DataInput var1) throws IOException {
        String className = Packet.readString(var1, 32767);
        this.infoClassName = className;

        try {
            Constructor<?> constructor = Class.forName(className).getDeclaredConstructor();
            constructor.setAccessible(true);
            SPacketForgingTableInfo.InfoType info = (SPacketForgingTableInfo.InfoType)constructor.newInstance();
            info.readData(var1);
            this.info = info;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException var5) {
            var5.printStackTrace();
        }

    }

    public void writePacketData(DataOutput var1) throws IOException {
        Packet.writeString(this.infoClassName, var1);
        this.info.writeData(var1);
    }

    public interface InfoType {
        String asString();

        int getColor();

        int getDataLength();

        void readData(DataInput var1) throws IOException;

        void writeData(DataOutput var1) throws IOException;
    }

    public static class EnhanceInfo implements SPacketForgingTableInfo.InfoType {
        private int chanceOfFailure;
        private String failFeedback;
        private int failData;
        private int duration;
        private int hammerDurabilityCost;
        private int axeDurabilityCost;

        private EnhanceInfo() {
        }

        private EnhanceInfo(int chanceOfFailure, String failFeedback, int failData, int duration, int hammerDurabilityCost, int axeDurabilityCost) {
            this.chanceOfFailure = chanceOfFailure;
            this.failFeedback = failFeedback;
            this.failData = failData;
            this.duration = duration;
            this.hammerDurabilityCost = hammerDurabilityCost;
            this.axeDurabilityCost = axeDurabilityCost;
        }

        public static SPacketForgingTableInfo.EnhanceInfo getInstance(int chanceOfFailure, String failFeedback, int failData, int duration, int hammerDurabilityCost, int axeDurabilityCost) {
            return new SPacketForgingTableInfo.EnhanceInfo(chanceOfFailure, failFeedback, failData, duration, hammerDurabilityCost, axeDurabilityCost);
        }

        public int getAxeDurabilityCost() {
            return this.axeDurabilityCost;
        }

        public int getChanceOfFailure() {
            return this.chanceOfFailure;
        }

        public int getDataLength() {
            return 16 + Packet.getPacketSizeOfString(this.failFeedback);
        }

        public int getDuration() {
            return this.duration;
        }

        public int getFailData() {
            return this.failData;
        }

        public String getFailFeedback() {
            return this.failFeedback;
        }

        public String asString() {
            return "";
        }

        public int getColor() {
            return 0;
        }

        public int getHammerDurabilityCost() {
            return this.hammerDurabilityCost;
        }

        public void readData(DataInput dataInput) throws IOException {
            this.chanceOfFailure = dataInput.readInt();
            this.failFeedback = Packet.readString(dataInput, 32767);
            this.failData = dataInput.readInt();
            this.duration = dataInput.readInt();
            this.hammerDurabilityCost = dataInput.readInt();
            this.axeDurabilityCost = dataInput.readInt();
        }

        public void writeData(DataOutput dataOutput) throws IOException {
            dataOutput.writeInt(this.chanceOfFailure);
            Packet.writeString(this.failFeedback, dataOutput);
            dataOutput.writeInt(this.failData);
            dataOutput.writeInt(this.duration);
            dataOutput.writeInt(this.hammerDurabilityCost);
            dataOutput.writeInt(this.axeDurabilityCost);
        }
    }

    public static class Failed implements SPacketForgingTableInfo.InfoType {
        private Failed() {

        }

        public static SPacketForgingTableInfo.Failed getInstance() {
            return new SPacketForgingTableInfo.Failed();
        }

        public String asString() {
            return LocaleI18n.translateToLocal("gui.forgingTable.failed");
        }

        public int getColor() {
            return 11141120;
        }

        public int getDataLength() {
            return 0;
        }

        public void readData(DataInput dataInput) {
        }

        public void writeData(DataOutput dataOutput) {
        }
    }

    public static class ReqItems implements SPacketForgingTableInfo.InfoType {
        private List<ItemStack> items = new ArrayList();

        private ReqItems() {
        }

        public static SPacketForgingTableInfo.ReqItems of(List<ItemStack> items) {
            SPacketForgingTableInfo.ReqItems reqItems = new SPacketForgingTableInfo.ReqItems();
            reqItems.items = items;
            return reqItems;
        }

        public String asString() {
            StringBuilder sb = new StringBuilder();
            sb.append(LocaleI18n.translateToLocal("gui.forgingTable.item_req"));
            sb.append(": ");
            Iterator var2 = this.items.iterator();

            while(var2.hasNext()) {
                ItemStack item = (ItemStack)var2.next();
                sb.append(LocaleI18n.translateToLocal(item.getMITEStyleDisplayName()));
                sb.append("X").append(item.stackSize).append(",");
            }

            return sb.toString();
        }

        public int getColor() {
            return EnumChatFormat.LIGHT_GRAY.rgb;
        }

        public int getDataLength() {
            int size = 0;

            ItemStack item;
            for(Iterator var2 = this.items.iterator(); var2.hasNext(); size += Packet.getPacketSizeOfItemStack(item)) {
                item = (ItemStack)var2.next();
            }

            return size;
        }

        public void readData(DataInput dataInput) throws IOException {
            int i = dataInput.readInt();

            for(int i1 = 0; i1 < i; ++i1) {
                this.items.add(Packet.readItemStack(dataInput));
            }

        }

        public void writeData(DataOutput out) throws IOException {
            out.writeInt(this.items.size());
            Iterator var2 = this.items.iterator();

            while(var2.hasNext()) {
                ItemStack item = (ItemStack)var2.next();
                Packet.writeItemStack(item, out);
            }

        }
    }

    public static class Succeed implements SPacketForgingTableInfo.InfoType {
        private Succeed() {
        }

        public static SPacketForgingTableInfo.Succeed getInstance() {
            return new SPacketForgingTableInfo.Succeed();
        }

        public String asString() {
            return LocaleI18n.translateToLocal("gui.forgingTable.succeed");
        }

        public int getColor() {
            return 5635925;
        }

        public int getDataLength() {
            return 0;
        }

        public void readData(DataInput dataInput) {
        }

        public void writeData(DataOutput dataOutput) {
        }
    }

    public static class ToolInfo implements SPacketForgingTableInfo.InfoType {
        private SPacketForgingTableInfo.ToolInfo.Tool tool;

        private ToolInfo(SPacketForgingTableInfo.ToolInfo.Tool tool) {
            this.tool = tool;
        }

        private ToolInfo() {
        }

        public static SPacketForgingTableInfo.ToolInfo of(SPacketForgingTableInfo.ToolInfo.Tool tool) {
            return new SPacketForgingTableInfo.ToolInfo(tool);
        }

        public String asString() {
            return LocaleI18n.translateToLocal(this.tool.getTranslationKey());
        }

        public int getColor() {
            return 11141120;
        }

        public int getDataLength() {
            return 2;
        }

        public void readData(DataInput dataInput) throws IOException {
            this.tool = SPacketForgingTableInfo.ToolInfo.Tool.values()[dataInput.readInt()];
        }

        public void writeData(DataOutput dataOutput) throws IOException {
            dataOutput.writeInt(this.tool.ordinal());
        }

        public enum Tool {
            AXE("gui.forgingTable.needAxe"),
            HAMMER("gui.forgingTable.needHammer");

            private final String translationKey;

            Tool(String translationKey) {
                this.translationKey = translationKey;
            }

            public String getTranslationKey() {
                return this.translationKey;
            }
        }
    }
}
