/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CashManagementUiTestModule } from '../../../test.module';
import { CmErrorDeleteDialogComponent } from 'app/entities/cm-error/cm-error-delete-dialog.component';
import { CmErrorService } from 'app/entities/cm-error/cm-error.service';

describe('Component Tests', () => {
    describe('CmError Management Delete Component', () => {
        let comp: CmErrorDeleteDialogComponent;
        let fixture: ComponentFixture<CmErrorDeleteDialogComponent>;
        let service: CmErrorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CashManagementUiTestModule],
                declarations: [CmErrorDeleteDialogComponent]
            })
                .overrideTemplate(CmErrorDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CmErrorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CmErrorService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
