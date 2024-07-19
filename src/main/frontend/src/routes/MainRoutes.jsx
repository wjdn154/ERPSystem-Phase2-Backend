import { lazy } from 'react';

import MainLayout from '../layout/MainLayout';
// import Loadable from 'ui-component/Loadable';

// const DashboardDefault = Loadable(lazy(() => import('views/dashboard')));
// const UtilsTypography = Loadable(lazy(() => import('views/utilities/Typography')));
// const UtilsColor = Loadable(lazy(() => import('views/utilities/Color')));
// const UtilsShadow = Loadable(lazy(() => import('views/utilities/Shadow')));
// const SamplePage = Loadable(lazy(() => import('views/sample-page')));


const MainRoutes = {
  path: '/',
  element: <MainLayout />
};

export default MainRoutes;
