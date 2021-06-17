import sys
import os

from SRGAN.Resolve import resolve
from LPD.LPD import LPD

import cv2
import numpy as np


def save_img(path, img):
    extension = os.path.splitext(path)[1]
    
    result, encoded_img = cv2.imencode(extension, img)
    if result:
        with open(path, mode='w+b') as f:
            encoded_img.tofile(f)
        return True
    
    return False


if __name__ == '__main__':
    img_path = ''
    for i in range(1, len(sys.argv)):
        img_path += ' ' + sys.argv[i]
    img_path = img_path[1:]

    if os.path.exists(img_path):
        plate_img = LPD(img_path)

        hr_img = resolve(plate_img)

        if not save_img('cropped_hr_image.jpg', np.uint8(hr_img)):
            print('Failed to save HR image')