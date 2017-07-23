package learn.com.newssimple.main.presenter;

import learn.com.newssimple.R;
import learn.com.newssimple.main.view.MainView;

/**
 * Created by zyx on 2017/6/14.
 */

public class MainPresenterImpl implements MainPresenter{
    private MainView mMainView;

    public MainPresenterImpl(MainView mMainView) {
        this.mMainView = mMainView;
    }

    @Override
    public void switchNavigation(int id) {
      switch (id){
          case R.id.navigation_item_news:
              mMainView.switch2News();
              break;
          case R.id.navigation_express:
              mMainView.switch2Express();
              break;
          default:
              mMainView.switch2News();
              break;
      }
    }
}
