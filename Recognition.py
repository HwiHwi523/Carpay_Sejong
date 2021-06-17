import easyocr
import cv2
import numpy as np
import os


def open_img(path):
    img_array = np.fromfile(path)
    return cv2.imdecode(img_array, cv2.IMREAD_COLOR)

def save_img(path, img):
    extension = os.path.splitext(path)[1]
    
    result, encoded_img = cv2.imencode(extension, img)
    if result:
        with open(path, mode='w+b') as f:
            encoded_img.tofile(f)
        return True
    
    return False


if __name__ == '__main__':
    image = open_img('cropped_hr_image.jpg')
    image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

    if save_img('gray_cropped_hr_image.jpg', image):

        reader = easyocr.Reader(['ko'], gpu=False)

        text = reader.readtext('gray_cropped_hr_image.jpg')
        if len(text) == 0:
            print('Failed to recognize')
            exit(0)

            
        convert_hangeul = { '갸':'가', '냐':'나', '댜':'다', '랴':'라', '먀':'마', '뱌':'바', '샤':'사', '야':'아', '쟈':'자'
                          , '겨':'거', '녀':'너', '뎌':'더', '려':'러', '며':'머', '벼':'버', '셔':'서', '여':'어', '져':'저'
                          , '교':'고', '뇨':'노', '됴':'도', '료':'로', '묘':'모', '뵤':'보', '쇼':'소', '요':'오', '죠':'조'
                          , '규':'구', '뉴':'누', '듀':'두', '류':'루', '뮤':'무', '뷰':'부', '슈':'수', '유':'우', '쥬':'주'
                          , '기':'거', '니':'너', '디':'더', '리':'러', '미':'머', '비':'버', '시':'서', '이':'어', '지':'저'
                          , '군':'구', '눈':'누', '둔':'두', '룬':'루', '문':'무', '분':'부', '순':'수', '운':'우', '준':'주'
                          , '간':'가', '난':'나', '단':'다', '란':'라', '만':'마', '반':'바', '산':'사', '안':'아', '잔':'자'
                          , '곤':'고', '논':'노', '돈':'도', '론':'로', '몬':'모', '본':'보', '손':'소', '온':'오', '존':'조'
                          , '건':'거', '넌':'너', '던':'더', '런':'러', '먼':'머', '번':'버', '선':'서', '언':'어', '전':'저'
                          , '카':'가', '커':'거', '코':'고', '쿠':'구'
                          , '타':'다', '터':'더', '토':'도', '투':'두'
                          , '햐':'하', '혀':'허', '효':'호', '히':'허', '후':'우'
                          , '게':'거', '네':'너', '데':'더', '레':'러', '메':'머', '베':'버', '세':'서', '에':'어', '제':'저'
                          , '계':'거', '녜':'너', '뎨':'더', '례':'러', '몌':'머', '볘':'버', '셰':'서', '예':'어', '졔':'저'
                          , '개':'거', '내':'너', '대':'더', '래':'러', '매':'머', '배':'버', '새':'서', '애':'어', '재':'저'
                          , '걔':'거', '냬':'너', '댸':'더', '럐':'러', '먜':'머', '뱨':'버', '섀':'서', '얘':'어', '쟤':'저'}
        hangeul_count = 0
        result_text = ''
        for line in text:
            for ch in line[1]:
                if '가' <= ch <= '힣':
                    hangeul_count += 1
                    if ch in convert_hangeul: # 오인식 된 한글일 시 변환
                        result_text += convert_hangeul[ch]
                    else:
                        result_text += ch
                if '0' <= ch <= '9':
                    result_text += ch
                if len(result_text) > 8:
                    break

        # 앞과 뒤에 오는 한글 제거
        hangeul_front_idx = -1
        while hangeul_front_idx + 1 < len(result_text) and '가' <= result_text[hangeul_front_idx + 1] <= '힣':
            hangeul_front_idx += 1

        hangeul_back_idx = len(result_text)
        while 0 <= hangeul_back_idx - 1 and '가' <= result_text[hangeul_back_idx - 1] <= '힣':
            hangeul_back_idx -= 1

        result_text = result_text[hangeul_front_idx + 1:hangeul_back_idx]

        # 맨앞의 숫자가 3글자 이상이라면 2글자만 남겨놓고 가장 앞부터 숫자 자르기 Ex) 102버 0321 -> 02버 0321
        num_count = 0
        rm_idx = -1

        while rm_idx + 1 < len(result_text) and '0' <= result_text[rm_idx + 1] <= '9':
            rm_idx += 1
            num_count += 1

        if 3 <= num_count:
            result_text = result_text[num_count - 2:]

        # 맨뒤의 숫자가 5글자 이상이라면 4글자만 남겨놓고 가장 뒤부터 숫자 자르기 Ex) 02버 03211 -> 02버 0321
        num_count = 0
        rm_idx = len(result_text)

        while 0 <= rm_idx - 1 and '0' <= result_text[rm_idx - 1] <= '9':
            rm_idx -= 1
            num_count += 1

        if 5 <= num_count:
            result_text = result_text[:len(result_text) - (num_count - 4)]
            
        with open('result.txt', mode='w') as f:
            f.write(result_text)
    