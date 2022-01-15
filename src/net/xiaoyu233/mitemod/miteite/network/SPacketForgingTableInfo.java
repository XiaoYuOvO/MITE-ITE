package net.xiaoyu233.mitemod.miteite.network;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.recipe.ForgingTableLevel;
import net.xiaoyu233.mitemod.miteite.item.recipe.IFaultFeedback;

import javax.annotation.Nullable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

public class SPacketForgingTableInfo extends Packet {
    private int infoTypeIndex;
    private SPacketForgingTableInfo.InfoType info;

    public SPacketForgingTableInfo() {
    }

    public SPacketForgingTableInfo(SPacketForgingTableInfo.InfoType info) {
        this.info = info;
        this.infoTypeIndex = this.info.getType().ordinal();
    }

    public SPacketForgingTableInfo.InfoType getInfo() {
        return this.info;
    }

    public int getPacketSize() {
        return 4 + 4 + this.info.getDataLength();
    }

    public void processPacket(Connection var1) {
        var1.processForgingTableInfoPacket(this);
    }

    public void readPacketData(DataInput var1) throws IOException {
        int infoTypeIndex = var1.readInt();
        this.infoTypeIndex = infoTypeIndex;

        try {
            SPacketForgingTableInfo.InfoType info = InfoTypes.values()[infoTypeIndex].getCtor().get();
            info.readData(var1);
            this.info = info;
        } catch (ArrayIndexOutOfBoundsException var5) {
            var5.printStackTrace();
        }

    }

    public void writePacketData(DataOutput var1) throws IOException {
        var1.writeInt(this.infoTypeIndex);
        this.info.writeData(var1);
    }

    enum InfoTypes {
        Enhance(EnhanceInfo::new),
        Failed(Failed::new),
        ReqItems(ReqItems::new),
        Succeed(Succeed::new),
        TableLevelInfo(TableLevelInfo::new),
        ToolInfo(ToolInfo::new);
        private final Supplier<InfoType> ctor;

        InfoTypes(Supplier<InfoType> ctor) {
            this.ctor = ctor;
        }

        public Supplier<InfoType> getCtor() {
            return ctor;
        }
    }

    public interface InfoType {
        String asString();

        int getColor();

        int getDataLength();

        InfoTypes getType();

        void readData(DataInput var1) throws IOException;

        void writeData(DataOutput var1) throws IOException;
    }

    public static class EnhanceInfo implements SPacketForgingTableInfo.InfoType {
        public static class FaultFeedbackData{
            private final int data;
            private final String name;

            private FaultFeedbackData(int data, String name) {
                this.data = data;
                this.name = name;
            }

            public int getDataLength(){
                return Packet.getPacketSizeOfString(this.name) + 4;
            }

            public int getData() {
                return data;
            }

            public String getName() {
                return name;
            }
        }
        private int chanceOfFailure;
        private int duration;
        private int hammerDurabilityCost;
        private int axeDurabilityCost;
        private final List<FaultFeedbackData> faultFeedbacks = new ArrayList<>();
        private EnhanceInfo() {
        }

        private EnhanceInfo(int chanceOfFailure, List<IFaultFeedback> faultFeedbacks, int duration, int hammerDurabilityCost, int axeDurabilityCost) {
            this.chanceOfFailure = chanceOfFailure;
            this.duration = duration;
            this.hammerDurabilityCost = hammerDurabilityCost;
            this.axeDurabilityCost = axeDurabilityCost;
            if (faultFeedbacks != null){
                for (IFaultFeedback faultFeedback : faultFeedbacks) {
                    if (faultFeedback != null){
                        this.faultFeedbacks.add(new FaultFeedbackData(faultFeedback.getData(),faultFeedback.getName()));
                    }
                }
            }
        }

        public static SPacketForgingTableInfo.EnhanceInfo getInstance(int chanceOfFailure, @Nullable List<IFaultFeedback> faultFeedbacks, int duration, int hammerDurabilityCost, int axeDurabilityCost) {
            return new SPacketForgingTableInfo.EnhanceInfo(chanceOfFailure, faultFeedbacks, duration, hammerDurabilityCost, axeDurabilityCost);
        }

        public int getAxeDurabilityCost() {
            return this.axeDurabilityCost;
        }

        public int getChanceOfFailure() {
            return this.chanceOfFailure;
        }

        public int getDataLength() {
            return 16 + this.faultFeedbacks.stream().mapToInt(FaultFeedbackData::getDataLength).sum();
        }

        @Override
        public InfoTypes getType() {
            return InfoTypes.Enhance;
        }

        public int getDuration() {
            return this.duration;
        }

        public List<FaultFeedbackData> getFaultFeedbacks() {
            return faultFeedbacks;
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
            int failFeedbackCount = dataInput.readInt();
            for (int i = 0; i < failFeedbackCount; i++) {
                this.faultFeedbacks.add(new FaultFeedbackData(dataInput.readInt(),Packet.readString(dataInput, 32767)));
            }
            this.duration = dataInput.readInt();
            this.hammerDurabilityCost = dataInput.readInt();
            this.axeDurabilityCost = dataInput.readInt();
        }

        public void writeData(DataOutput dataOutput) throws IOException {
            dataOutput.writeInt(this.chanceOfFailure);
            dataOutput.writeInt(this.faultFeedbacks.size());
            for (FaultFeedbackData faultFeedback : this.faultFeedbacks) {
                dataOutput.writeInt(faultFeedback.getData());
                Packet.writeString(faultFeedback.getName(), dataOutput);
            }
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

        @Override
        public InfoTypes getType() {
            return InfoTypes.Failed;
        }

        public void readData(DataInput dataInput) {
        }

        public void writeData(DataOutput dataOutput) {
        }
    }

    public static class ReqItems implements SPacketForgingTableInfo.InfoType {
        private List<ItemStack> items = new ArrayList<>();

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

            for (ItemStack item : this.items) {
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

            for (ItemStack itemStack : this.items) {
                size += Packet.getPacketSizeOfItemStack(itemStack);
            }

            return size;
        }

        public void readData(DataInput dataInput) throws IOException {
            int i = dataInput.readInt();

            for(int i1 = 0; i1 < i; ++i1) {
                this.items.add(Packet.readItemStack(dataInput));
            }

        }

        @Override
        public InfoTypes getType() {
            return InfoTypes.ReqItems;
        }

        public void writeData(DataOutput out) throws IOException {
            out.writeInt(this.items.size());

            for (ItemStack item : this.items) {
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

        @Override
        public InfoTypes getType() {
            return InfoTypes.Succeed;
        }
    }

    public static class TableLevelInfo implements SPacketForgingTableInfo.InfoType{
        private ForgingTableLevel req;
        @Override
        public String asString() {
            return LocaleI18n.translateToLocal("gui.forgingTable.levelToLow." + req.name().toLowerCase(Locale.ROOT));
        }

        private TableLevelInfo(){}

        private TableLevelInfo(ForgingTableLevel req){
            this.req = req;
        }

        public static TableLevelInfo of(ForgingTableLevel req){
            return new TableLevelInfo(req);
        }

        @Override
        public int getColor() {
            return 0xaa0000;
        }

        @Override
        public int getDataLength() {
            return 6;
        }

        @Override
        public void readData(DataInput var1) throws IOException {
            this.req = ForgingTableLevel.values()[var1.readInt()];
        }

        @Override
        public void writeData(DataOutput var1) throws IOException {
            var1.writeInt(this.req.ordinal());
        }

        @Override
        public InfoTypes getType() {
            return InfoTypes.TableLevelInfo;
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
            return 0xaa0000;
        }

        public int getDataLength() {
            return 2 + 4;
        }

        public void readData(DataInput dataInput) throws IOException {
            this.tool = SPacketForgingTableInfo.ToolInfo.Tool.values()[dataInput.readInt()];
        }

        public void writeData(DataOutput dataOutput) throws IOException {
            dataOutput.writeInt(this.tool.ordinal());
        }

        @Override
        public InfoTypes getType() {
            return InfoTypes.ToolInfo;
        }

        public enum Tool {
            AXE("gui.forgingTable.needAxe"),
            HAMMER("gui.forgingTable.needHammer"),
            MAX_LEVEL("gui.forgingTable.max_level_or_not_found");

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
