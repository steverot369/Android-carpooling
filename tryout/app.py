from flask import Flask, render_template, request, redirect, url_for
import os
import cv2
import numpy as np

app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = 'uploads'

def overlay_shirt(base_image_path, shirt_image_path, output_image_path, shirt_position):
    base_image = cv2.imread(base_image_path)
    shirt_image = cv2.imread(shirt_image_path, cv2.IMREAD_UNCHANGED)

    shirt_x, shirt_y = shirt_position

    # Resize the shirt to fit the upper body
    shirt_width = base_image.shape[1] - 2 * shirt_x
    shirt_height = int(shirt_width * (shirt_image.shape[0] / shirt_image.shape[1]))
    resized_shirt = cv2.resize(shirt_image, (shirt_width, shirt_height))

    # Extract the alpha (transparency) channel from the resized shirt
    shirt_mask = resized_shirt[:, :, 3] / 255.0

    # Create an alpha channel for the base image (all ones)
    base_alpha = np.ones_like(base_image[:, :, 0], dtype=float)

    # Apply the shirt mask to each channel of the base image
    for c in range(3):
        base_image[shirt_y:shirt_y+shirt_height, shirt_x:shirt_x+shirt_width, c] = \
            base_image[shirt_y:shirt_y+shirt_height, shirt_x:shirt_x+shirt_width, c] * (1 - shirt_mask) + \
            resized_shirt[:, :, c] * shirt_mask

    # Save the result
    cv2.imwrite(output_image_path, base_image)


@app.route('/')
def index():
    return render_template('upload.html')

@app.route('/upload', methods=['POST'])
def upload():
    if 'file' not in request.files:
        return redirect(request.url)

    file = request.files['file']

    if file.filename == '':
        return redirect(request.url)

    if file:
        filename = os.path.join(app.config['UPLOAD_FOLDER'], 'your_image.jpg')
        file.save(filename)

        # Overlay the shirt on the uploaded image
        overlay_shirt(
            'uploads/your_image.jpg',
            'static/shirts/shirt.png',
            'uploads/your_image_with_shirt.jpg',
            shirt_position=(100, 200)  # Adjust the position as needed
        )

        return redirect(url_for('result'))

@app.route('/result')
def result():
    return render_template('result.html')

if __name__ == '__main__':
    app.run(debug=True)
