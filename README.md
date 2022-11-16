## 구현완료
  - [x] board 에서 쪽지 기능을 사용 할 수 있게 board에 accout id 넣어주기
  - [x] 회원가입시 email이나 username이 중복이면 같은 오류 메세지를 보냈는데 오류 메시지 분리시키기 
  - [x] 토큰을 이용해 남의 메시지까지 건드릴수 있었던거 막아놓기
  - [x] 토큰 재발행 api/v1/user/refresh
  - [x] 유저별 전체 게시글 api/v1/board/user?user_id=*

## 구현해야 할 내용
  - [ ] 비밀번호 hash화
  - [ ] message API - 기본 receiver , sender 가 email로 되어있는데 email을 알수가 없음 -> id값을 넣어도 동작하고, email 값을 넣어도 동작하도록 수정해 드리겠음.
  - [ ] 이미지 처리 어떻게 할것인지
  - [ ] 로그아웃 처리