
let comment_list;

function init() {
    fetch("http://localhost:8080/api/comment?url=http://localhost:8080").then(res => {
        return res.json();
    }).then(data => {
        comment_list = data;
    });
}

function main() {
    init()
}

main()