# easy-comment

spring boot를 이용한 댓글 서비스 REST API 서버입니다

## 댓글 등록하기

`/api/comment` url로 POST 요청을 보내서 댓글을 등록할 수 있습니다

### Request

```
POST /api/comment?url=comment.zziri.com HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Cache-Control: no-cache

{
	"author":"jihoon",
	"password":"123123",
	"content":"hello"
}
```

### Response

```
HTTP/1.1 201 Created
Content-Type: application/json

{
	"id": 1,
	"author": "zziri",
	"content": "hello",
	"date": "2020-12-22T22:26:05"
}
```

## 댓글 목록 가져오기

`/api/comment` url로 GET 요청을 보내서 댓글 목록을 가져올 수 있습니다

### Request

```
GET /api/comment?url=comment.zziri.com HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Cache-Control: no-cache
```

### Response

```
HTTP/1.1 200 OK
Content-Type: application/json

[
    {
        "id": 1,
        "author": "zziri",
        "content": "hello",
        "date": "2020-12-28T22:46:17"
    },
    {
        "id": 2,
        "author": "zziri",
        "content": "world",
        "date": "2020-12-28T22:46:21"
    }
]
```

## 댓글 내용 수정하기

`/api/comment` url로 PATCH 요청을 보내서 댓글의 내용을 수정할 수 있습니다

### Request

```
PATCH /api/comment?url=comment.zziri.com HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Cache-Control: no-cache

{
	"id":"1",
	"password":"1234",
	"content":"안녕"
}
```

### Response

```
HTTP/1.1 200 OK
Content-Type: application/json

{
    "id": 1,
    "author": "zziri",
    "content": "안녕",
    "date": "2020-12-28T22:49:08"
}
```

## 댓글 삭제하기

`/api/comment` url로 DELETE 요청을 보내서 댓글을 삭제할 수 있습니다

### Request

```
DELETE /api/comment?url=comment.zziri.com HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Cache-Control: no-cache

{
	"id":"1",
	"password":"1234"
}
```

### Response

```
HTTP/1.1 204 No Content

```