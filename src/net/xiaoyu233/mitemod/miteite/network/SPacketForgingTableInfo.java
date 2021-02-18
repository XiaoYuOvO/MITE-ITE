package net.xiaoyu233.mitemod.miteite.network;

import net.minecraft.*;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SPacketForgingTableInfo extends Packet {
    private String infoClassName;
    private InfoType info;
    public SPacketForgingTableInfo() {

    }

    public InfoType getInfo() {
        return info;
    }

    public SPacketForgingTableInfo(InfoType info){
        this.info = info;
        this.infoClassName = this.info.getClass().getName();
    }
    @Override
    public void a(DataInput var1) throws IOException {
        String className = Packet.a(var1, 32767);
        this.infoClassName = className;
        try {
            Constructor<?> constructor = Class.forName(className).getDeclaredConstructor();
            constructor.setAccessible(true);
            InfoType info = (InfoType) constructor.newInstance();
            info.readData(var1);
            this.info = info;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void a(DataOutput var1) throws IOException {
        Packet.a(infoClassName,var1);
        this.info.writeData(var1);
    }

    @Override
    public void a(Connection var1) {
        var1.processForgingTableInfoPacket(this);
    }

    @Override
    public int a() {
        return 4 + Packet.getPacketSizeOfString(infoClassName) + info.getDataLength();
    }

    public interface InfoType{
        //Call in client
        String asString();
        int getColor();
        void writeData(DataOutput dataOutput) throws IOException;
        void readData(DataInput dataInput) throws IOException;
        int getDataLength();
    }

    public static class ReqItems implements InfoType{
        private List<ItemStack> items = new ArrayList<>();
        private ReqItems(){
            int i = 0;
        }
        public static ReqItems of(List<ItemStack> items){
            ReqItems reqItems = new ReqItems();
            reqItems.items = items;
            return reqItems;
        }
        @Override
        public String asString() {
            StringBuilder sb = new StringBuilder();
            sb.append(LocaleI18n.a("gui.forgingTable.item_req"));
            sb.append(": ");
            for (ItemStack item : this.items) {
                sb.append(LocaleI18n.a(item.getMITEStyleDisplayName()));
                sb.append("X").append(item.b).append(",");
            }
            return sb.toString();
        }

        @Override
        public int getColor() {
            return EnumChatFormat.LIGHT_GRAY.rgb;
        }

        @Override
        public void writeData(DataOutput out) throws IOException {
            out.writeInt(this.items.size());
            for (ItemStack item : this.items) {
                Packet.a(item,out);
            }
        }

        @Override
        public void readData(DataInput dataInput) throws IOException {
            int i = dataInput.readInt();
            for (int i1 = 0; i1 < i; i1++) {
                this.items.add(Packet.c(dataInput));
            }
        }

        @Override
        public int getDataLength() {
            int size = 0;
            for (ItemStack item : this.items) {
                size += Packet.getPacketSizeOfItemStack(item);
            }
            return size;
        }
    }

    public static class ToolInfo implements InfoType{
        public enum Tool{
            AXE("gui.forgingTable.needAxe"),
            HAMMER("gui.forgingTable.needHammer");
            private final String translationKey;
            Tool(String translationKey) {
                this.translationKey = translationKey;
            }

            public String getTranslationKey() {
                return translationKey;
            }
        }
        private Tool tool;
        @Override
        public String asString() {
            return LocaleI18n.a(this.tool.getTranslationKey());
        }

        public static ToolInfo of(Tool tool){
            return new ToolInfo(tool);
        }

        private ToolInfo(Tool tool){
            this.tool = tool;
        }

        private ToolInfo(){
            int i = 0;
        }

        @Override
        public int getColor() {
            return 0xAA0000;
        }

        @Override
        public void writeData(DataOutput dataOutput) throws IOException {
            dataOutput.writeInt(this.tool.ordinal());
        }

        @Override
        public void readData(DataInput dataInput) throws IOException {
            this.tool = Tool.values()[dataInput.readInt()];
        }

        @Override
        public int getDataLength() {
            return 2;
        }
    }

    public static class Failed implements InfoType{
        public static Failed getInstance(){
            return new Failed();
        }
        private Failed(){
            int i = 0;
        }

        @Override
        public String asString() {
            return LocaleI18n.a("gui.forgingTable.failed");
        }

        @Override
        public int getColor() {
            return 0xAA0000;
        }

        @Override
        public void writeData(DataOutput dataOutput) {

        }

        @Override
        public void readData(DataInput dataInput) {

        }

        @Override
        public int getDataLength() {
            return 0;
        }
    }

    public static class Succeed implements InfoType{
        private Succeed() {
            int i = 0;
        }

        public static Succeed getInstance() {
            return new Succeed();
        }

        @Override
        public String asString() {
            return LocaleI18n.a("gui.forgingTable.succeed");
        }

        @Override
        public int getColor() {
            return 0x55FF55;
        }

        @Override
        public void writeData(DataOutput dataOutput) {

        }

        @Override
        public void readData(DataInput dataInput) {

        }

        @Override
        public int getDataLength() {
            return 0;
        }
    }

    public static class EnhanceInfo implements InfoType{
        private int chanceOfFailure;
        private String failFeedback;
        private int failData;
        private int duration;
        private int hammerDurabilityCost;
        private int axeDurabilityCost;

        public int getChanceOfFailure() {
            return chanceOfFailure;
        }

        public int getAxeDurabilityCost() {
            return axeDurabilityCost;
        }

        public int getHammerDurabilityCost() {
            return hammerDurabilityCost;
        }

        public int getDuration() {
            return duration;
        }

        public int getFailData() {
            return failData;
        }

        public String getFailFeedback() {
            return failFeedback;
        }

        public static EnhanceInfo getInstance(int chanceOfFailure, String failFeedback, int failData, int duration,int hammerDurabilityCost,int axeDurabilityCost) {
            return new EnhanceInfo(chanceOfFailure, failFeedback, failData, duration, hammerDurabilityCost, axeDurabilityCost);
        }

        private EnhanceInfo(){
            int i = 0;
        }

        private EnhanceInfo(int chanceOfFailure, String failFeedback, int failData, int duration, int hammerDurabilityCost, int axeDurabilityCost){
            this.chanceOfFailure = chanceOfFailure;
            this.failFeedback = failFeedback;
            this.failData = failData;
            this.duration = duration;
            this.hammerDurabilityCost = hammerDurabilityCost;
            this.axeDurabilityCost = axeDurabilityCost;
        }

        @Override
        //Dont use
        public String asString() {
            return "";
        }

        @Override
        //Dont use
        public int getColor() {
            return 0;
        }

        @Override
        public void writeData(DataOutput dataOutput) throws IOException {
            dataOutput.writeInt(chanceOfFailure);
            Packet.a(this.failFeedback,dataOutput);
            dataOutput.writeInt(failData);
            dataOutput.writeInt(duration);
            dataOutput.writeInt(hammerDurabilityCost);
            dataOutput.writeInt(axeDurabilityCost);
        }

        @Override
        public void readData(DataInput dataInput) throws IOException {
            this.chanceOfFailure = dataInput.readInt();
            this.failFeedback = Packet.a(dataInput,32767);
            this.failData = dataInput.readInt();
            this.duration = dataInput.readInt();
            this.hammerDurabilityCost = dataInput.readInt();
            this.axeDurabilityCost = dataInput.readInt();
        }

        @Override
        public int getDataLength() {
            return 3 + 3 + 4 + 4 + 2 + Packet.getPacketSizeOfString(this.failFeedback);
        }
    }
}
