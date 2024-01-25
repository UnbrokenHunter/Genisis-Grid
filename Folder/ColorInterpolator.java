package Folder;

import java.awt.Color;
import java.util.Hashtable;

public class ColorInterpolator {
        private final Color[] colors;
        private final int numColors;

        // Constructor for two colors
        public ColorInterpolator(Color startColor, Color endColor, int numColors) {
                if (startColor == null || endColor == null) {
                        throw new IllegalArgumentException("Colors cannot be null.");
                }

                this.colors = new Color[] { startColor, endColor };
                this.numColors = numColors;
        }

        // Constructor for an array of colors
        public ColorInterpolator(Color[] colors, int numColors) {
                if (colors == null || colors.length < 2) {
                        throw new IllegalArgumentException("There must be at least two colors to interpolate.");
                }

                this.colors = colors;
                this.numColors = numColors;
        }

        public Color getColor(int step) {
                if (step >= numColors) {
                        return colors[colors.length - 1];
                }

                float totalColorIntervals = colors.length - 1;
                float stepsPerColorInterval = numColors / totalColorIntervals;

                int colorIndex = (int) (step / stepsPerColorInterval);
                float ratioWithinCurrentInterval = (step % stepsPerColorInterval) / stepsPerColorInterval;

                return interpolateColor(colors[colorIndex], colors[colorIndex + 1], ratioWithinCurrentInterval);
        }

        private Color interpolateColor(Color c1, Color c2, float ratio) {
                int red = interpolate(c1.getRed(), c2.getRed(), ratio);
                int green = interpolate(c1.getGreen(), c2.getGreen(), ratio);
                int blue = interpolate(c1.getBlue(), c2.getBlue(), ratio);

                return new Color(red, green, blue);
        }

        private int interpolate(int start, int end, float ratio) {
                return (int) (start + ratio * (end - start));
        }

        // PRESETS
        // Different color palettes

        // The "Standard" palette fading from bright yellow to dark grey
        public static final Color[] STANDARD = {
                        Color.YELLOW, // Bright yellow
                        Color.DARK_GRAY // Dark grey
        };

        // A palette of oceanic blues
        public static final Color[] OCEANIC_BLUES = {
                        new Color(240, 248, 255), // Alice blue
                        new Color(0, 191, 255), // Deep sky blue
                        new Color(25, 25, 112) // Midnight blue
        };

        // A palette of soft pastels
        public static final Color[] SOFT_PASTELS = {
                        new Color(255, 182, 193), // Light pink
                        new Color(173, 216, 230), // Light blue
                        new Color(255, 239, 213) // Papaya whip
        };

        // A palette of cool blue shades
        public static final Color[] COOL_BLUES = {
                        new Color(173, 216, 230), // Light blue
                        new Color(100, 149, 237), // Cornflower blue
                        new Color(70, 130, 180) // Steel blue
        };

        // A palette of warm sunset shades
        public static final Color[] WARM_SUNSET = {
                        Color.YELLOW, // Bright yellow
                        new Color(255, 99, 71), // Tomato red
                        new Color(148, 0, 211) // Dark violet
        };

        // A palette of earthy greens
        public static final Color[] EARTHY_GREENS = {
                        new Color(144, 238, 144), // Light green
                        new Color(107, 142, 35), // Olive drab
                        new Color(85, 107, 47) // Dark olive green
        };

        // A palette of fiery reds
        public static final Color[] FIERY_REDS = {
                        Color.RED, // Bright red
                        new Color(255, 69, 0), // Bright orange-red
                        new Color(139, 0, 0) // Dark red
        };

        // A palette of elegant purples
        public static final Color[] ELEGANT_PURPLES = {
                        new Color(147, 112, 219), // Medium purple
                        new Color(138, 43, 226), // Blue-violet
                        new Color(75, 0, 130) // Indigo
        };

        // A palette of vibrant teals
        public static final Color[] VIBRANT_TEALS = {
                        new Color(0, 128, 128), // Teal
                        new Color(64, 224, 208), // Turquoise
                        new Color(127, 255, 212) // Aquamarine
        };

        // A palette of rich browns
        public static final Color[] RICH_BROWNS = {
                        new Color(165, 42, 42), // Brown
                        new Color(210, 105, 30), // Chocolate
                        new Color(139, 69, 19) // Saddle brown
        };

        // A palette of calming lavenders
        public static final Color[] CALMING_LAVENDERS = {
                        new Color(230, 230, 250), // Lavender
                        new Color(221, 160, 221), // Plum
                        new Color(153, 50, 204) // Dark orchid
        };

        // A palette of golden hues
        public static final Color[] GOLDEN_HUES = {
                        new Color(255, 215, 0), // Gold
                        new Color(238, 232, 170), // Pale goldenrod
                        new Color(189, 183, 107) // Dark khaki
        };

        // A palette of pinks
        public static final Color[] CHERRY_BLOSSOM = {
                        new Color(239, 146, 239), // Pink
                        new Color(255, 217, 253), // Light Pink
                        new Color(232, 45, 223), // Dark Pink
                        new Color(239, 146, 239), // Pink
                        new Color(255, 217, 253), // Light Pink
                        new Color(246, 107, 240), // Mid Pink
        };

        // A palette of pinks
        public static final Color[] CHRISTMAS = {
                        new Color(194, 7, 0), // Light Red
                        new Color(184, 47, 47), // Red
                        new Color(20, 56, 32), // Dark Green
                        new Color(255, 255, 255), // White
                        new Color(11, 117, 48), // Green
                        new Color(23, 194, 83), // Light Green
                        new Color(255, 255, 255), // White
                        new Color(194, 7, 0), // Light Red
                        new Color(184, 47, 47), // Red
                        new Color(20, 56, 32), // Dark Green
                        new Color(255, 255, 255), // White
                        new Color(11, 117, 48), // Green
                        new Color(23, 194, 83), // Light Green
                        new Color(255, 255, 255), // White
                        new Color(194, 7, 0), // Light Red
                        new Color(184, 47, 47), // Red
                        new Color(20, 56, 32), // Dark Green
                        new Color(255, 255, 255), // White
                        new Color(11, 117, 48), // Green
                        new Color(23, 194, 83), // Light Green
                        new Color(255, 255, 255), // White
                        new Color(194, 7, 0), // Light Red
        };

        // A palette of monochromatic shades
        public static final Color[] MONOCHROMATIC = {
                        new Color(192, 192, 192), // Silver
                        new Color(128, 128, 128), // Gray
                        new Color(105, 105, 105) // Dim Gray
        };

        // A palette of refreshing mints
        public static final Color[] REFRESHING_MINTS = {
                        new Color(152, 251, 152), // Pale Green
                        new Color(127, 255, 170), // Aquamarine
                        new Color(60, 179, 113) // Medium Sea Green
        };

        // A palette of desert hues
        public static final Color[] DESERT_HUES = {
                        new Color(250, 235, 215), // Antique White
                        new Color(245, 222, 179), // Wheat
                        new Color(210, 180, 140) // Tan
        };

        // A palette of bright neons
        public static final Color[] BRIGHT_NEONS = {
                        new Color(173, 255, 47), // Green Yellow
                        new Color(255, 20, 147), // Deep Pink
                        new Color(50, 205, 50), // Lime Green
                        new Color(255, 140, 0) // Dark Orange
        };

        // A palette of mystic woods
        public static final Color[] MYSTIC_WOODS = {
                        new Color(0, 100, 0), // Dark Green
                        new Color(85, 107, 47), // Dark Olive Green
                        new Color(34, 139, 34) // Forest Green
        };

        // A palette of ice and fire
        public static final Color[] ICE_AND_FIRE = {
                        new Color(0, 191, 255), // Deep Sky Blue
                        new Color(240, 128, 128), // Light Coral
                        new Color(255, 255, 240), // Ivory
                        new Color(220, 20, 60) // Crimson
        };

        // A palette of space and stars
        public static final Color[] SPACE_AND_STARS = {
                        new Color(25, 25, 112), // Midnight Blue
                        new Color(72, 61, 139), // Dark Slate Blue
                        new Color(255, 250, 250) // Snow
        };

        // A palette of sunrise and dawn
        public static final Color[] SUNRISE_AND_DAWN = {
                        new Color(255, 165, 0), // Orange
                        new Color(218, 112, 214), // Orchid
                        new Color(255, 192, 203) // Pink
        };

        // A palette of cosmic vibes
        public static final Color[] COSMIC_VIBES = {
                        new Color(138, 43, 226), // Blue Violet
                        Color.white,
                        new Color(148, 0, 211), // Dark Violet
                        Color.yellow,
                        new Color(123, 104, 238) // Medium Slate Blue
        };

        // Midnight Masquerade Colors
        public static final Color[] MIDNIGHT_MASQUERADE = {
                        new Color(10, 10, 45), // Deep Midnight: Dark blue-almost-black
                        new Color(80, 40, 90), // Royal Velvet: Rich purple
                        new Color(100, 65, 130), // Lavish Lilac: Lighter purple
                        new Color(145, 20, 60), // Majestic Maroon: Deep red
                        new Color(240, 200, 75), // Gilded Gold: Bright gold
        };

        // Dawn's Caress Colors
        public static final Color[] DAWNS_CARESS = {
                        new Color(250, 230, 210), // Gentle Glow: Soft yellow
                        new Color(255, 180, 150), // Rosy Horizon: Peachy-pink
                        new Color(225, 110, 90), // Blushing Dawn: Slightly darker peach
                        new Color(190, 80, 60), // Amber Warmth: Warm red-orange
                        new Color(150, 45, 30), // Deep Ember: Dark reddish-brown
        };

        // Whimsical Wonderland Colors
        public static final Color[] WHIMSICAL_WONDERLAND = {
                        new Color(130, 220, 230), // Crystal Blue: Bright cyan
                        new Color(255, 140, 250), // Candy Floss: Light magenta
                        new Color(90, 180, 80), // Enchanted Forest: Vibrant green
                        new Color(245, 210, 80), // Sunlit Gold: Bright yellow
                        new Color(230, 90, 170), // Mystic Magenta: Deep magenta
        };

        // Vintage Voyage Colors
        public static final Color[] VINTAGE_VOYAGE = {
                        new Color(200, 190, 160), // Aged Parchment: Light beige
                        new Color(140, 110, 80), // Time-Worn Leather: Medium brown
                        new Color(95, 75, 55), // Vintage Bookbind: Dark brown
                        new Color(210, 170, 130), // Faded Canvas: Muted beige
                        new Color(185, 140, 90), // Sepia Memories: Warm medium brown
        };

        // Celestial Dreams Colors
        public static final Color[] CELESTIAL_DREAMS = {
                        new Color(5, 15, 40), // Starless Night: Very dark blue
                        new Color(0, 50, 100), // Cosmic Ocean: Dark blue
                        new Color(0, 80, 180), // Galactic Blue: Bright blue
                        new Color(40, 10, 65), // Nebula Purple: Dark purple
                        new Color(140, 40, 90), // Astral Pink: Pinkish-purple
        };

        // Lost Atlantis Colors
        public static final Color[] LOST_ATLANTIS = {
                        new Color(7, 83, 89), // Abyssal Blue: Deep, dark ocean blue
                        new Color(22, 142, 134), // Turquoise Lagoon: Vibrant teal
                        new Color(240, 219, 79), // Sunken Gold: Rich, warm gold
                        new Color(188, 38, 55), // Coral Remnants: Muted reddish-pink
                        new Color(45, 29, 48), // Mystic Algae: Dark violet-tinged marine
                        new Color(95, 147, 161), // Salt Spray Azure: Soft, muted blue
        };

        // Enchanted Forest Symphony Colors
        public static final Color[] FOREST_SYMPHONY = {
                        new Color(16, 42, 15), // Midnight Pine: Deep forest green
                        new Color(42, 93, 34), // Emerald Canopy: Rich green
                        new Color(185, 122, 87), // Faery Bark: Warm muted brown
                        new Color(223, 177, 128), // Dew-Kissed Fern: Light beige with a hint of green
                        new Color(136, 50, 42), // Woodland Lullaby: Dark rust red
                        new Color(94, 140, 106), // Whispering Willow: Muted greenish-teal
        };

        // Desert Mirage Colors
        public static final Color[] DESERT_MIRAGE = {
                        new Color(235, 211, 182), // Sun-Bleached Dune: Light sandy beige
                        new Color(192, 70, 46), // Mirage Red: Warm, earthy red
                        new Color(44, 42, 65), // Twilight Veil: Deep, dark purple
                        new Color(144, 154, 172), // Silver Oasis: Metallic light gray-blue
                        new Color(121, 88, 59), // Ancient Artifact: Rustic brown
                        new Color(98, 135, 148), // Phantom Blue: Illusive muted blue
        };

        // Lunar Lullabies Colors
        public static final Color[] LUNAR_LULLABIES = {
                        new Color(24, 19, 34), // Moonlit Shadow: Dark violet-tinged black
                        new Color(155, 140, 192), // Lavender Eclipse: Soft pastel purple
                        new Color(225, 223, 238), // Starlight Whisper: Light gray with a hint of lavender
                        new Color(59, 56, 95), // Galactic Silence: Deep muted indigo
                        new Color(193, 165, 121), // Celestial Dust: Warm muted beige
                        new Color(108, 92, 123), // Nebula Drift: Muted lavender gray
        };

        // A palette representing the colors of the rainbow
        public static final Color[] RAINBOW = {
                        Color.RED,
                        Color.ORANGE,
                        Color.YELLOW,
                        Color.GREEN,
                        Color.BLUE,
                        new Color(75, 0, 130), // Indigo
                        new Color(143, 0, 255) // Violet
        };

        private static final Hashtable<String, Color[]> PALETTES;

        static {
                PALETTES = new Hashtable<>();
                PALETTES.put("Standard", STANDARD);
                PALETTES.put("Cool Blues", COOL_BLUES);
                PALETTES.put("Warm Sunset", WARM_SUNSET);
                PALETTES.put("Earthy Greens", EARTHY_GREENS);
                PALETTES.put("Fiery Reds", FIERY_REDS);
                PALETTES.put("Elegant Purple", ELEGANT_PURPLES);
                PALETTES.put("Oceanic Blues", OCEANIC_BLUES);
                PALETTES.put("Soft Pastels", SOFT_PASTELS);
                PALETTES.put("Vibrant Teals", VIBRANT_TEALS);
                PALETTES.put("Rich Browns", RICH_BROWNS);
                PALETTES.put("Calming Lavenders", CALMING_LAVENDERS);
                PALETTES.put("Golden Hues", GOLDEN_HUES);
                PALETTES.put("Cherry Blossom", CHERRY_BLOSSOM);
                PALETTES.put("Christmas", CHRISTMAS);
                PALETTES.put("Monochromatic", MONOCHROMATIC);
                PALETTES.put("Refreshing Mints", REFRESHING_MINTS);
                PALETTES.put("Desert Hues", DESERT_HUES);
                PALETTES.put("Bright Neons", BRIGHT_NEONS);
                PALETTES.put("Mystic Woods", MYSTIC_WOODS);
                PALETTES.put("Fire & Ice", ICE_AND_FIRE);
                PALETTES.put("Space & Stars", SPACE_AND_STARS);
                PALETTES.put("Sunrise & Dawn", SUNRISE_AND_DAWN);
                PALETTES.put("Cosmic Vibes", COSMIC_VIBES);
                PALETTES.put("Midnight Masquerade", MIDNIGHT_MASQUERADE);
                PALETTES.put("Dawn's Caress", DAWNS_CARESS);
                PALETTES.put("Whimsical Wonderland", WHIMSICAL_WONDERLAND);
                PALETTES.put("Vintage Voyage", VINTAGE_VOYAGE);
                PALETTES.put("Celestial Dreams", CELESTIAL_DREAMS);
                PALETTES.put("Lost Atlantis", LOST_ATLANTIS);
                PALETTES.put("Enchanted Forest Symphony", FOREST_SYMPHONY);
                PALETTES.put("Desert Mirage", DESERT_MIRAGE);
                PALETTES.put("Lunar Lullabies", LUNAR_LULLABIES);
                PALETTES.put("Rainbow", RAINBOW);
        }

        public static Hashtable<String, Color[]> getPalettes() {
                return PALETTES;
        }

        public static Color[] getPalette(String name) {
                return PALETTES.get(name);
        }
}