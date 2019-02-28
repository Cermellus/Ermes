/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ErmesTestModule } from '../../../test.module';
import { CallLogActionComponent } from 'app/entities/call-log-action/call-log-action.component';
import { CallLogActionService } from 'app/entities/call-log-action/call-log-action.service';
import { CallLogAction } from 'app/shared/model/call-log-action.model';

describe('Component Tests', () => {
    describe('CallLogAction Management Component', () => {
        let comp: CallLogActionComponent;
        let fixture: ComponentFixture<CallLogActionComponent>;
        let service: CallLogActionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CallLogActionComponent],
                providers: []
            })
                .overrideTemplate(CallLogActionComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CallLogActionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CallLogActionService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CallLogAction(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.callLogActions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
