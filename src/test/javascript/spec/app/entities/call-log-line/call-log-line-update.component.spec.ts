/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ErmesTestModule } from '../../../test.module';
import { CallLogLineUpdateComponent } from 'app/entities/call-log-line/call-log-line-update.component';
import { CallLogLineService } from 'app/entities/call-log-line/call-log-line.service';
import { CallLogLine } from 'app/shared/model/call-log-line.model';

describe('Component Tests', () => {
    describe('CallLogLine Management Update Component', () => {
        let comp: CallLogLineUpdateComponent;
        let fixture: ComponentFixture<CallLogLineUpdateComponent>;
        let service: CallLogLineService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CallLogLineUpdateComponent]
            })
                .overrideTemplate(CallLogLineUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CallLogLineUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CallLogLineService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CallLogLine(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.callLogLine = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CallLogLine();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.callLogLine = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
