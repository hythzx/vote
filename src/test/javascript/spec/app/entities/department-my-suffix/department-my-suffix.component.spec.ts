/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { VoteTestModule } from '../../../test.module';
import { DepartmentMySuffixComponent } from '../../../../../../main/webapp/app/entities/department-my-suffix/department-my-suffix.component';
import { DepartmentMySuffixService } from '../../../../../../main/webapp/app/entities/department-my-suffix/department-my-suffix.service';
import { DepartmentMySuffix } from '../../../../../../main/webapp/app/entities/department-my-suffix/department-my-suffix.model';

describe('Component Tests', () => {

    describe('DepartmentMySuffix Management Component', () => {
        let comp: DepartmentMySuffixComponent;
        let fixture: ComponentFixture<DepartmentMySuffixComponent>;
        let service: DepartmentMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoteTestModule],
                declarations: [DepartmentMySuffixComponent],
                providers: [
                    DepartmentMySuffixService
                ]
            })
            .overrideTemplate(DepartmentMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DepartmentMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepartmentMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new DepartmentMySuffix(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.departments[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
