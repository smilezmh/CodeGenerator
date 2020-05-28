package cmtech.soft.equipment.utils.Delegate;

import cmtech.soft.equipment.utils.Delegate.EventHanderler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

interface INotifier {
    void nofify();
}
