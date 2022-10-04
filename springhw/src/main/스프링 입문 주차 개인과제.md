## Use Case

![유스케이스](https://user-images.githubusercontent.com/87157566/193710122-df3ccbf7-9afa-49cc-8902-beb2a63b9834.png)

## API 명세서

![api설계](https://user-images.githubusercontent.com/87157566/193710166-aa4ea453-1ea2-4c0f-836b-a6844bb881c6.png)

#### 요구사항
1. 전체 게시글 목록 조회 API <br>
   - 제목, 작성자명, 작성 날짜를 조회하기 <br>
   - 작성 날짜 기준으로 내림차순 정렬하기 <br>
2. 게시글 작성 API <br>
   - 제목, 작성자명, 비밀번호, 작성 내용을 입력하기 <br>
3. 게시글 조회 API <br>
   - 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기  <br>
    (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.) <br>
4. 게시글 비밀번호 확인 API <br>
   - 비밀번호를 입력 받아 해당 게시글의 비밀번호와 일치여부 판단하기 <br>
5. 게시글 수정 API <br>
   - 제목, 작성자명, 비밀번호, 작성 내용을 수정되게 하기 <br>
6. 게시글 삭제 API <br>
   - 글이 삭제되게 하기 <br>

### 답변

1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body) <br>
수정은 param 과 body, 삭제는 param 방식을 사용했습니다.

2. 어떤 상황에 어떤 방식의 request를 써야하나요? <br>
내용을 작성하거나 수정할 때 body를 사용하고, id값을 가져와 해당 id에 대응하는 내용을 수정하고 삭제해야 할 때는 param을 사용해야 합니다.

3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?
  - URL 규칙을 적용시켰습니다 <br>
URL이름을 "postings" 복수형, 소문자로 사용했고 get, post와 같은 행위와 파일 확장자를 URL명에 포함시키지 않았습니다. <br>
  - HTTP 메서드 GET, POST, PUT, DELETE 를 사용했습니다

4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service) <br>
네

5. 작성한 코드에서 빈(Bean)을 모두 찾아보세요! <br>
postingService, postingRepository

6. API 명세서 작성 가이드라인을 검색하여 직접 작성한 명세서와 비교해보세요!

