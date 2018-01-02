/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { VoteTestModule } from '../../../test.module';
import { EmployeeMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/employee-my-suffix/employee-my-suffix-detail.component';
import { EmployeeMySuffixService } from '../../../../../../main/webapp/app/entities/employee-my-suffix/employee-my-suffix.service';
import { EmployeeMySuffix } from '../../../../../../main/webapp/app/entities/employee-my-suffix/employee-my-suffix.model';

describe('Component Tests', () => {

    describe('EmployeeMySuffix Management Detail Component', () => {
        let comp: EmployeeMySuffixDetailComponent;
        let fixture: ComponentFixture<EmployeeMySuffixDetailComponent>;
        let service: EmployeeMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoteTestModule],
                declarations: [EmployeeMySuffixDetailComponent],
                providers: [
                    EmployeeMySuffixService
                ]
            })
            .overrideTemplate(EmployeeMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new EmployeeMySuffix(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.employee).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
