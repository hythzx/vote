/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { VoteTestModule } from '../../../test.module';
import { TaskMySuffixComponent } from '../../../../../../main/webapp/app/entities/task-my-suffix/task-my-suffix.component';
import { TaskMySuffixService } from '../../../../../../main/webapp/app/entities/task-my-suffix/task-my-suffix.service';
import { TaskMySuffix } from '../../../../../../main/webapp/app/entities/task-my-suffix/task-my-suffix.model';

describe('Component Tests', () => {

    describe('TaskMySuffix Management Component', () => {
        let comp: TaskMySuffixComponent;
        let fixture: ComponentFixture<TaskMySuffixComponent>;
        let service: TaskMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoteTestModule],
                declarations: [TaskMySuffixComponent],
                providers: [
                    TaskMySuffixService
                ]
            })
            .overrideTemplate(TaskMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TaskMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaskMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new TaskMySuffix(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tasks[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
