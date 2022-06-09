import Vue from 'vue';

import VuetifyDialog from 'vuetify-dialog';
import VueRouter from 'vue-router';
import 'vuetify-dialog/dist/vuetify-dialog.css';
import {router} from './router';
import Vuetify from "vuetify";
import vuetify from '../plugins/vuetify';
import App from '@/App';
import HttpService from "http-service/service";

Vue.use(Vuetify)
Vue.use(VuetifyDialog, {
    context: {
        vuetify,
        router
    }
});
Vue.prototype.http = new HttpService('http://localhost/')

Vue.use(VueRouter)

new Vue({
    vuetify,
    router,
    render: h => h(App)
}).$mount('#app');