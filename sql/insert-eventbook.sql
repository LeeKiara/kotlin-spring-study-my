select * from event_books;

select * from books where item_id in (select item_id from event_books);

-- 이벤트 하는 출판사로 변경
-- update event_books set publisher = '러브앤프리';

-- 이벤트 도서를 books에 등록
insert into books (publisher, title,author, description,item_id,cover,pub_date,price_standard,price_sales,
stock_status,link,isbn,isbn13,category_id, category_name, customer_review_rank)
select publisher, title,author, description,item_id,cover,"2022-04-18",20000,10000,"정상","","","",0,"",0
 from event_books;

SELECT books.id, event_books.item_id, event_books.title, event_books.description, event_books.cover, event_books.text_sentence, 
event_books.ment_sentence, event_books.author_image, event_books.author, event_books.publisher, event_books.author_description 
FROM event_books INNER JOIN books ON books.item_id = event_books.item_id WHERE event_books.title <> '';

INSERT INTO event_books (
    item_id,
    title,
    description,
    cover,
    text_sentence,
    ment_sentence,
    author_image,
    author,
    publisher,
    author_description
)
VALUES (
    -- item_id
    7014,
    -- title
    '아름다운 우리나라 전국 무장애 여행지 39',
    -- description
    '전국 무장애 여행지 39곳. 휠체어 타고 떠나는 #아름다운 우리나라 전국 무장애 여행지 39',
    -- cover
    'http://t1.daumcdn.net/lbook/image/6432508?timestamp=20230927151645',
    -- text_sentence
    '여행은 누구에게나 평등해야 한다. 접근 가능한 여행. 평등한 여행. 무장애 여행',
    -- ment_sentence
    '작가가 사랑한 한 문장',
    -- author_image
    '//img1.daumcdn.net/thumb/C200x200.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/mmq/image/HK7XgDzGMqlqoWbBGgRqAgwlsmc.jpg',
    -- author
    '전윤선',
    -- publisher
    '나무발전소',
    -- author_description
    '전윤선의 브런치입니다. 여행작가 에세이스트 입니다'
);

INSERT INTO event_books (
    item_id,
    title,
    description,
    cover,
    text_sentence,
    ment_sentence,
    author_image,
    author,
    publisher,
    author_description    
)
VALUES (
    -- item_id
    7020,
    -- title
    '반역자와 배신자들',
    -- description
    '2차대전 중 첨예한 논란이 되는 14인의 인물에 대한 이야기 입니다. 역사 속 인물에 대한 평가는 그들이 처했던 상황이나 시대적 변화에 따라 다양한 스펙트럼으로 해석될 수 있음을 전달하고 싶었습니다.',
    -- cover
    'http://t1.daumcdn.net/lbook/image/6427524?timestamp=20230902172215',
    -- text_sentence
    '과연 누가 배신자인가?',
    -- ment_sentence
    '작가가 사랑한 한 문장',
    -- author_image
    '//img1.daumcdn.net/thumb/C200x200.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/dfBO/image/8SlaePfyDITdkvd2L3Nkjoco9Is',
    -- author
    '이준호',
    -- publisher
    '눌와',
    -- author_description
    '20년 이상 전세계를 무대로 해외영업과 마케팅 부문에서 일하고 있습니다. 역사, 문화 및 해외의 다양한 이야기들을 함께 공유하고 싶습니다.'    
);


INSERT INTO event_books (
    item_id,
    title,
    description,
    cover,
    text_sentence,
    ment_sentence,
    author_image,
    author,
    publisher,
    author_description
    
)
VALUES (
    -- item_id
    7020,
    -- title
    '50, 이제 결혼합니다',
    -- description
    '결혼 기피 시대에 외치는 만혼의 행복. 속박은 다운, 편안함은 업! 결혼과 비혼 사이에서 고민하는 중년들에게 유용한 힌트를 담았습니다. 청춘의 문턱을 막 넘어선 분들도 ‘선행 학습’ 삼아 확인해 보시면 좋겠습니다.',
    -- cover
    'https://t1.daumcdn.net/brunch/static/img/banner/9791192642055.jpg',
    -- text_sentence
    '성숙한 나이에 하는 결혼, 그것은 가장 영리하고 지혜로운 선택일 수 있다.',
    -- ment_sentence
    '작가가 사랛한 한 문장',
    -- author_image
    '//img1.daumcdn.net/thumb/C200x200.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/dL46/image/2HfsauQITevnJN3sD9-A8YPIQF8.jpg',
    -- author
    '백지성',
    -- publisher
    '오르골',
    -- author_description
    '만혼, 재혼을 고민하는 사람들을 위해 글을 썼습니다. 오랜 세월 좌충우돌 외롭게 살아오다 50에 결혼하며 느낀, 중년의 결혼과 삶, 행복에 대한 생각들을 나누고자 글을 씁니다.'
    
);

INSERT INTO event_books (
    item_id,
    title,
    description,
    cover,
    text_sentence,
    ment_sentence,
    author_image,
    author,
    publisher,
    author_description
    
)
VALUES (
    -- item_id
    7034,
    -- title
    '3분 진료 공장의 세계',
    -- description
    '1시간을 넘게 기다려 3분도 안 돼 끝나는 진료, 의사들은 환자와 눈조차 맞추지 않으려 한다. 대학병원의 진료실은 왜 모두에게 불평불만의 공간이 되었을까? ‘3분 진료 공장’이 되어버린 우리 의료계의 현실을 짚어본다',
    -- cover
    'http://t1.daumcdn.net/lbook/image/6430811?timestamp=20230905184640',
    -- text_sentence
    '누군가는 힘겨웠던 삶을 마감하고, 또 다른 누군가는 두려움과 불안에 떨며 진료실로 들어온다.',
    -- ment_sentence
    '작가가 사랛한 한 문장',
    -- author_image
    '//img1.daumcdn.net/thumb/C200x200.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/wdm/image/WPUa5gY1qlqmk-ZkHsRAjgcQPmM.jpg',
    -- author
    'OncoAzim',
    -- publisher
    '두리반',
    -- author_description
    '<잃었지만 잊지 않은 것들> (2019, 라이킷)을 썼습니다. 한 대학병원의 종양내과에서 일합니다. 언젠가는 웃기는 책과 만화책을 내고 싶습니다.'
);

INSERT INTO event_books (
    item_id,
    title,
    description,
    cover,
    wrap_book_sentence,
    text_sentence,
    ment_sentence,
    author_image,
    author,
    publisher,
    author_description
    
)
VALUES (
    -- item_id
    7034,
    -- title
    '3분 진료 공장의 세계',
    -- description
    '1시간을 넘게 기다려 3분도 안 돼 끝나는 진료, 의사들은 환자와 눈조차 맞추지 않으려 한다. 대학병원의 진료실은 왜 모두에게 불평불만의 공간이 되었을까? ‘3분 진료 공장’이 되어버린 우리 의료계의 현실을 짚어본다',
    -- cover
    'http://t1.daumcdn.net/lbook/image/6430811?timestamp=20230905184640',
    -- wrap_book_sentence
    '누군가는 힘겨웠던 삶을 마감하고, 또 다른 누군가는 두려움과 불안에 떨며 진료실로 들어온다.',
    -- text_sentence
    '작가가 사랑한 한 문장',
    -- ment_sentence
    '작가가 사랑한 한 문장',
    -- author_image
    '//img1.daumcdn.net/thumb/C200x200.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/wdm/image/WPUa5gY1qlqmk-ZkHsRAjgcQPmM.jpg',
    -- author
    'OncoAzim',
    -- publisher
    '두리반',
    -- author_description
    '<잃었지만 잊지 않은 것들> (2019, 라이킷)을 썼습니다. 한 대학병원의 종양내과에서 일합니다. 언젠가는 웃기는 책과 만화책을 내고 싶습니다.'
    
);

INSERT INTO event_books (
    item_id,
    title,
    description,
    cover,
    text_sentence,
    ment_sentence,
    author_image,
    author,
    publisher,
    author_description
)
VALUES (
    -- item_id
    7043,
    -- title_book
    '어디에나 있고 어디에도 없는 나나랜드',
    -- book_description
    '평범한 1990년생이 질문을 던지고 행복을 찾아 노력했던 발자국 모음이다. 4개국 거주, 36개국 여행 뒤 돌아와 내린 결론은, 숨 쉬고, 배우고, 사랑하고, 성장했던 모든 곳이 결국 나만의 ‘나나랜드’였다!',
    -- wrap_book_image
    'http://t1.daumcdn.net/lbook/image/6432436?timestamp=20230907132022',
    -- wrap_book_sentence
    '나를 둘러싼 환경이 바뀌면 나 자신뿐만 아니라 주변 사람과의 관계나 환경에 대해 수많은 질문이 떠오른다.',
    -- wrap_text_sentence
    '작가가 사랑한 한 문장',
    -- user_image
    '//img1.daumcdn.net/thumb/C200x200.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/akp/image/Be22p7saKnctljYKefDlCF1qRUE.jpeg',
    -- user_name
    '개화걸 김도희',
    -- publisher_book
    '모놀로그',
    -- user_description
    '스무 살까지 여권도 없던 극한의 모범생에서 4개국 거주, 36개국 여행, 사랑하는 영국남자와 결혼했어요. 다양한 문화의 관점에서 일상에 \'왜\'를 질문합니다.'
);



