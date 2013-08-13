exports.deleteButton = function (path, id) {
    var action = path + '/' + id;
    return '<div class="delete-button">\n' +
        '<form action="' + action + '"' + 'method="post">\n' +
        '    <input type="hidden" name="_method" value="delete"/>\n' +
        '    <input type="submit" value="Delete">\n' +
        '</form>\n</div>\n';
}