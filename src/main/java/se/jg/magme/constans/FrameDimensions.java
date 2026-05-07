package se.jg.magme.constans;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Getter

public class FrameDimensions {

    private FrameDimensions() {
        throw new UnsupportedOperationException("Static class - do not make an instance of this");
    }
    private static final Map<FrameId, FrameDimension> dimensionMap;
    static {
        dimensionMap = new EnumMap<>(FrameId.class);
        //1993 style frame
        dimensionMap.put(
                FrameId.F1993,
                new FrameDimension(
                        36,
                        450,
                        26,
                        57,
                        24,
                        4)
        );
        //1997 style frame
        dimensionMap.put(
                FrameId.F1997,
                new FrameDimension(
                        51,
                        450,
                        29,
                        58,
                        24,
                        4)
        );
        //2003 style frame
        dimensionMap.put(
                FrameId.F2003,
                new FrameDimension(
                        41,
                        447,
                        35,
                        69,
                        25,
                        3)
        );
        //2015 style frame
        FrameDimension fd2015 = new FrameDimension(
                41,
                451,
                35,
                64,
                24,
                2);
        dimensionMap.put(
                FrameId.F2015,
                fd2015
        );
        //2015 style frame used as fallback dimension for unknown frames
        dimensionMap.put(
                FrameId.FDEFAULT,
                fd2015);
    }

    public static FrameDimension getFrameDimension(String frameId) {
        FrameId fd;
        try {
            fd = FrameId.valueOf("F" + frameId);
        } catch (IllegalArgumentException e) {
            fd = FrameId.FDEFAULT;
        }
        return dimensionMap.get(fd);
    }

    public static List<String> getSupportedFrameIds() {
        return dimensionMap.keySet().stream().map(Enum::name).map(s -> s.substring(1)).toList();
    }

    @AllArgsConstructor @Getter
    public static final class FrameDimension {
        private final int nameBoxLeftX;
        private final int manaBoxRightX;
        private final int nameManaRegionTopY;
        private final int nameManaRegionBottomY;
        private final int manaSymbolDiameter;
        private final int manaSymbolDistance;

        public int nameManaRegionHeight() {
            return nameManaRegionBottomY - nameManaRegionTopY;
        }
    }
}

enum FrameId  {
    F1993, F1997, F2003, F2015, FDEFAULT
}