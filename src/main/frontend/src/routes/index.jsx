import { createBrowserRouter } from 'react-router-dom';

// routes
import MainRoutes from './MainRoutes';

const router = createBrowserRouter([MainRoutes], {
  basename: import.meta.env.VITE_APP_BASE_NAME
});

export default router;
