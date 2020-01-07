

var MyTabModel = Backbone.Collection.extend({
    url: 'data/codecoverage.json'
})
class MyLayout extends allure.components.AppLayout {

    initialize() {
        this.model = new MyTabModel();
    }

    loadData() {
        return this.model.fetch();
    }

    getContentView() {
        return new MyView({items: this.model.models,
                           id:"view_coco",
                           attributes: {'style':'height:100%'}

        });
    }



}

const template = function (data) {
    var hasJa;
    for (var item of data.items) {
        hasJa = item.attributes.hasJa;
    }
    if ("on"== hasJa) {
           html ='<iframe name="footer" marginwidth=10 marginheight=10 width="100%" height="100%" src="data/jacoco-report/index.html" frameborder=0></iframe>' ;
     } else {
            html = '<h3 class="pane__title"> Jacoco report not found </h3>';
     }

    return html;
}
//在allure报告的左边增加一个tab按钮
var MyView = Backbone.Marionette.View.extend({
    template: template,

    render: function () {
        this.$el.html(this.template(this.options));
        return this;
    }
})

// 定义一个新标签，该标签将显示在左窗格菜单上，名称为tabName
allure.api.addTab('codecoverage', {
    title: 'CodeCoverage', icon: 'fa fa-trophy',
    route: 'codecoverage',
    onEnter: (function () {
       mylayout = new MyLayout()

        return mylayout
    })
});
