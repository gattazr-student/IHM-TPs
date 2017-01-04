#ifndef RADIALMENU_H
#define RADIALMENU_H

#include <QWidget>
#include <QLabel>
#include <math.h>

class RadialMenu : public QWidget
{
    Q_OBJECT
signals:

public slots:

private:
    /**
     * @brief The menu_entry struct
     */
    struct menu_entry {
        QLabel* label;
        enum{MENU, ACTION} type;
        union {
            RadialMenu* rw;
            std::function<void(void)>* action;
        };
    };

    /**
     * @brief The direction enum
     */
    enum direction {
        EAST = 0,
        NORTH_EAST = 4,
        NORTH = 1,
        NORTH_WEST = 5,
        WEST = 2,
        SOUTH_WEST = 6,
        SOUTH = 3,
        SOUTH_EAST = 7,
        ERROR
    };

    /** Entries in the menu */
    std::vector<menu_entry> menu_entries;
    // Distance between center and a label
    const int distance = 100;

    /**
     * Create a label the given string as content
     * @brief createLabel
     * @param string content of label
     * @return created label
     */
    QLabel* createLabel(const std::string& content);

    /* Mouse EVENTS */
    void mousePressEvent(QMouseEvent* qm) override;
    void mouseReleaseEvent(QMouseEvent* qm) override;
    void mouseMoveEvent(QMouseEvent* qm) override;

public:
    explicit RadialMenu(QWidget *parent = 0);

    void addEntry(std::string name, std::function<void(void)>* action);
    void addEntry(std::string name, RadialMenu* menu);

    // Sets the menu's center position and displays it
    void showMenu(const QPoint& position);
    void hideMenu();
};







#define PI 3.14159265358979323846
inline QPoint rotationGlobal(const QPoint& toRot, const QPoint& center, double angle, bool deg=true) {
    if (deg == true)
        angle = angle / 180 * PI;

    double x = toRot.x();
    double y = toRot.y();
    double x_temp;

    x -= center.x();
    y -= center.y();
    x_temp = x;
    x = x*cos(angle) - y*sin(angle);
    y = y*cos(angle) + x_temp*sin(angle);
    x += center.x();
    y += center.y();

    return QPoint(x, y);
}

#endif // RADIALMENU_H
