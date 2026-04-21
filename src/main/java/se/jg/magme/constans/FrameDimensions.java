package se.jg.magme.constans;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumMap;
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
                FrameId.Y1993,
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
                FrameId.Y1997,
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
                FrameId.Y2003,
                new FrameDimension(
                        41,
                        447,
                        35,
                        67,
                        23,
                        1)
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
                FrameId.Y2015,
                fd2015
        );
        //2015 style frame used as fallback dimension for unknown frames
        dimensionMap.put(
                FrameId.DEFAULT,
                fd2015);
    }

    public static FrameDimension getFrameDimension(String frameId) {
        FrameId fd;
        try {
            fd = FrameId.valueOf("Y" + frameId);
        } catch (IllegalArgumentException e) {
            fd = FrameId.DEFAULT;
        }
        return dimensionMap.get(fd);
    }

    @AllArgsConstructor @Getter
    public static final class FrameDimension {
        private final int nameBoxLeftX;
        private final int manaBoxRightX;
        private final int nameManaRegionTopY;
        private final int nameManaRegionBottomY;
        private final int cmcDiameter;
        private final int cmcSymbolDistance;

        public int nameManaRegionHeight() {
            return nameManaRegionBottomY - nameManaRegionTopY;
        }
    }
}

enum FrameId  {
    Y1993, Y1997, Y2003, Y2015, DEFAULT
}