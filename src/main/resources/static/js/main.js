function getQuestion(id) {
    let url = window.location.href + "/" + id;
    window.location.href = url;
    console.log(id)
}