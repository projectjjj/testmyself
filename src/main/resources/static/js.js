
$(document).ready(function () {
    getBbs()
})
function getBbs(){
    $.ajax({
        type:"GET",
        url:"api/memos",
        success:function (response) {
            for (i=0;i<response.length; i++) {
                let card = response[i];
                console.log(card);
                let tempHtml = html(card);
                $('#bbs').append(tempHtml);
            }
        }
    })
}
function html(card) {
    return `<div class="bbs-user">
        <div class="emoge"></div>
        <div class="bbs-reply-text">
          <p class="text-reply-name">익명의 ${card.name}<span class="text-reply-date">${card.createdAt}</span><span>좋아요 3</span>
          <button onclick="modify(${card.id})">수정하기</button>
          <button onclick="deletes(${card.id})">삭제하기</button></p>
          <button onclick="like()">like</button>
        </div>
      </div>
      <input type="text" id="reply" placeholder="댓글남기기"/><button onclick="replySave(${card.id})">댓글저장</button>
      <p class="text-comment">${card.memo}</p>
    `
}
function postBbs() {
    let name = $('#name').val()
    let memo = $('#memo').val()
    if (name == '') {
        $('#name').focus();
        alert('이름을 입력해주세유')
        return;}
    if (memo == '') {
        $('#memo').focus();
        alert('한마디 해주세유')
        return;}
    $.ajax({
        type:"POST",
        url:"/api/memos",
        contentType:"application/json",
        data : JSON.stringify({'name':name, 'memo':memo}),
        success:function (response) {
            window.location.reload();
        }
    })
}

function deletes(id) {
    $.ajax({
        type:"DELETE",
        url:`/api/memos/${id}`,
        success: function (response) {
            alert("삭제!")
            window.location.reload();
        }
    })
}
let targetid;
function modify(id) {
    $("#container").addClass('active');
    targetid = id;
}

function memoModifySave() {
    let memoModify = $("#memoModify").val();
    $.ajax({
        type: "PUT",
        url: `/api/memos/${targetid}`,
        contentType: "application/json",
        data: JSON.stringify({'memo': memoModify}),
        success: function (response) {
            alert("수정!")
            window.location.reload();
        }
    })
}
function close() {
    $("#container").removeClass('active');
}

function replySave(id) {
    let reply = $('#reply').val();
    console.log(reply,id);
    $.ajax ({
        type:"POST",
        url:`/api/memos/${id}/reply`,
        contentType: "application/json",
        data: JSON.stringify({'reply': reply}),
        success:function (response) {
            alert("댓글써!!")
        }
    })
}