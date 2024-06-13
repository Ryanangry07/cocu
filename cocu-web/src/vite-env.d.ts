/// <reference types="vite/client" />

// import @component cannot find module error
declare module '*.vue'{
    import type { DefineComponent } from 'vue';
    const component: DefineComponent<{}, {}, any>
    export default component
}