from PIL import Image

def analyze_image(path):
    img = Image.open(path).convert('RGBA')
    colors = img.getcolors(maxcolors=256)
    print(f"=== {path} ===")
    if colors:
        # Sort by frequency
        sorted_colors = sorted(colors, key=lambda x: x[0], reverse=True)
        for count, col in sorted_colors:
            if col[3] > 0: # Non-transparent
                print(f"Color: {col}, Count: {count}, Hex: #{col[0]:02x}{col[1]:02x}{col[2]:02x}")

analyze_image(r"g:\myfirstmod\1.12.2\Tinkers' Crystex\src\main\resources\assets\tinkerscrystex\textures\items\wolframite_ingot.png")
analyze_image(r"g:\myfirstmod\1.12.2\Tinkers' Crystex\src\main\resources\assets\tinkerscrystex\textures\items\wolframite_nugget.png")
