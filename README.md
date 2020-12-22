# easy-comment

spring boot를 이용한 댓글 서비스 REST API 서버입니다

## REST API 사용법

REST API를 사용하는 방법을 설명합니다

### 댓글 등록하기

댓글을 등록하는 기능 사용방법입니다

#### Request

요청 URL : $server_ip:8080/api/comment

query : url

Body : 
```shell
{
	"author": "string",
	"content": "string",
	"password": "string"
}
```

content-type: application/json

#### Response

Http Status Code: 201 (Created)

Body:
```shell
{
	"id": 1
	"author": "string",
	"content": "string",
	"date": "2020-12-22T13:08:44"
}
```

#### Example

request:
```shell
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

response:
```
HTTP/1.1 201 Created
Content-Type: application/json

{
	"id": 1,
	"author": "jihoon",
	"content": "hello",
	"date": "2020-12-22T22:26:05"
}
```